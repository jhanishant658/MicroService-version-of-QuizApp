package QuizApp.MicroService.QuestionService.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import QuizApp.MicroService.QuestionService.Models.Question;
import QuizApp.MicroService.QuestionService.Repositories.QuestionRepository;

@Service
public class QuestionService {
    @Autowired 
    private QuestionRepository questionRepository ;
    public  ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<List<Question>> getQuestionByCategory(String category){
        return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
    }
    public ResponseEntity<Question> addQuestion(Question question){
        return new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED);
    }
    public ResponseEntity<List<String>> getQuestionForQuiz(String category, int numOfQuestions) {
        List<Question> questions = questionRepository.findByCategory(category) ; 
        if (questions.size() < numOfQuestions) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        }
        List<String> questionIds = questions.stream().limit(numOfQuestions).map(Question::getId).toList();
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }
    public ResponseEntity<Question> getQuestionById(String id) {
       return new ResponseEntity<>(questionRepository.findById(id).orElse(null), HttpStatus.OK);
    }
    
    public ResponseEntity<Integer> getMarks(String id , String selectedAns) {
        
       if(questionRepository.findById(id).orElse(null) == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
       if(questionRepository.findById(id).get().getCorrectAnswer().equals(selectedAns)) {
        return new ResponseEntity<>(1, HttpStatus.OK);
       }
         return new ResponseEntity<>(0, HttpStatus.OK);
    }
}
