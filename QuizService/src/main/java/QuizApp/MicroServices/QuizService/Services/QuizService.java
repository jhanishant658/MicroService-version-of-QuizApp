package QuizApp.MicroServices.QuizService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import QuizApp.MicroServices.QuizService.Model.Question;
import QuizApp.MicroServices.QuizService.Model.Quiz;
import QuizApp.MicroServices.QuizService.Repository.QuizRepository;
import QuizApp.MicroServices.QuizService.feign.QuestionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class QuizService {
    private int count = 0 ; 
    @Autowired
    private QuizRepository quizRepository ;
    @Autowired
    private QuestionService questionService ;
    Logger logger = LoggerFactory.getLogger(QuizService.class);
   
    @CircuitBreaker(name = "QuestionService", fallbackMethod = "createQuizFallback")
     @Retry(name = "QuestionServiceRetry")
    public ResponseEntity<Quiz> createQuiz(String category , String title , int numOfQuestions , String createdBy){
        count++;
        logger.info("Creating quiz with title: {}, category: {}, numOfQuestions: {}, createdBy: {}", title, category, numOfQuestions, createdBy);
        ResponseEntity<List<String>> response = questionService.getQuestionForQuiz(category, numOfQuestions);
        List<String> allQuestions = response.getBody();
    
     Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestions(allQuestions);
        quiz.setCreatedBy(createdBy);
       
        if(quiz.getQuestions().isEmpty()){
            logger.warn("⚠️ No questions received from Question Service for category: {}", category);
            return new ResponseEntity<>(quiz, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("✅ Successfully received {} questions from Question Service for category: {}", quiz.getQuestions().size(), category);
        }
        
        logger.info("Saving quiz with title: {}, category: {}, numOfQuestions: {}, createdBy: {}", title, category, numOfQuestions, createdBy);
         quizRepository.save(quiz);
       return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }   
     public ResponseEntity<Quiz> createQuizFallback(String category, String title, int numOfQuestions, String createdBy, Throwable e) {
        logger.warn("✅ FALLBACK CALLED - createQuizFallback");
        logger.info("createquiz retry count: {}", count);
        count = 0 ; // reset count after fallback is called
         logger.warn("Question Service is down. Returning fallback quiz with empty question list for category: {}", category);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestions(new ArrayList<>());
        quiz.setCreatedBy(createdBy);
        return new ResponseEntity<>(quiz, HttpStatus.SERVICE_UNAVAILABLE);
    }
   
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        logger.info("Fetching all quizzes");
        return new ResponseEntity<>(quizRepository.findAll(), HttpStatus.OK);
    }
     public ResponseEntity<List<Quiz>> getAllQuizzesByCategory(String category){
        logger.info("Fetching quizzes by category: {}", category);
        return new ResponseEntity<>(quizRepository.findByCategory(category), HttpStatus.OK);
    }
    @Retry(name = "QuestionService")
    @CircuitBreaker(name = "GetQuestionService", fallbackMethod = "getQuizByIdFallback")
    public ResponseEntity<List<Question>> getQuizById(@NonNull String id){
        count++;
        logger.info("Fetching questions for quiz with id: {} in service layer", id);
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if(quiz == null){
            logger.warn("Quiz not found for id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       List<String> questionIds = quiz.getQuestions();
       List<Question> questions = new ArrayList<>();
         for(String questionId : questionIds){
            Question question = questionService.getQuestionById(questionId).getBody();
            if(question != null){
                questions.add(question);
            }
        }
        Collections.shuffle(questions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
        public ResponseEntity<List<Question>> getQuizByIdFallback(String id , Throwable e){
            logger.warn("Question Service is down. Falling back to return empty question list for quiz id: {}", id);
            logger.info("getquizbyid retry count: {}", count);
            count = 0 ; // reset count after fallback is called
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
   
    
}
