package QuizApp.MicroServices.QuizService.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import QuizApp.MicroServices.QuizService.Model.Question;
import QuizApp.MicroServices.QuizService.Model.Quiz;
import QuizApp.MicroServices.QuizService.Repository.QuizRepository;
import QuizApp.MicroServices.QuizService.feign.QuestionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository ;
    @Autowired
    private QuestionService questionService ;
    @CircuitBreaker(name = "quizService", fallbackMethod = "createQuizFallback")
    public ResponseEntity<Quiz> createQuiz(String category , String title , int numOfQuestions , String createdBy){
        
        ResponseEntity<List<String>> response = questionService.getQuestionForQuiz(category, numOfQuestions);
        List<String> allQuestions = response.getBody();
    
     Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestions(allQuestions);
        quiz.setCreatedBy(createdBy);
        quizRepository.save(quiz);
       return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }   
      public ResponseEntity<Quiz>  createQuizFallback(String category , String title , int numOfQuestions , String createdBy , Throwable e){
        System.out.println("Question Service is down. Falling back to create quiz without questions.");
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestions(new ArrayList<>());
        quiz.setCreatedBy(createdBy);
       return new ResponseEntity<>(quiz, HttpStatus.CREATED);
      }
   
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        return new ResponseEntity<>(quizRepository.findAll(), HttpStatus.OK);
    }
     public ResponseEntity<List<Quiz>> getAllQuizzesByCategory(String category){
        return new ResponseEntity<>(quizRepository.findByCategory(category), HttpStatus.OK);
    }
    @CircuitBreaker(name = "quizService", fallbackMethod = "getQuizByIdFallback")
    public ResponseEntity<List<Question>> getQuizById(String id){
        Quiz quiz = quizRepository.findById(id).orElse(null);
        if(quiz == null){
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
            System.out.println("Question Service is down. Falling back to return empty question list for quiz id: " + id);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
   
    
}
