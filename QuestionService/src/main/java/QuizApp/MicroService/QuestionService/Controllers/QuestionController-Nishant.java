package QuizApp.MicroService.QuestionService.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import QuizApp.MicroService.QuestionService.Models.Question;
import QuizApp.MicroService.QuestionService.Response.QuizAnsResponse;
import QuizApp.MicroService.QuestionService.Services.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService ;
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return questionService.getAllQuestions();
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/category")
    public ResponseEntity<List<Question>> getQuestionByCategory(@RequestParam String category){
        try {
            return questionService.getQuestionByCategory(category);
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        try {
            return questionService.addQuestion(question);
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
       
    }
    @PostMapping("/getQuestionforQuiz")
    public ResponseEntity<List<String>> getQuestionForQuiz(@RequestParam String categories , @RequestParam int numOfQuestions){
        try {
            return questionService.getQuestionForQuiz(categories, numOfQuestions);
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/id")
    public ResponseEntity<Question> getQuestionById(@RequestParam String id){
        try {
            return questionService.getQuestionById(id);
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping("/getMarks")
    public ResponseEntity<Integer> getMarks(@RequestBody QuizAnsResponse quizAnsRes){
        try {
            return questionService.getMarks(quizAnsRes.getQuestionId(), quizAnsRes.getSelectedAnswer());
        } catch (Exception e) {
           e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
