package QuizApp.MicroServices.QuizService.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import QuizApp.MicroServices.QuizService.Model.Question;
import QuizApp.MicroServices.QuizService.Model.Quiz;
import QuizApp.MicroServices.QuizService.Services.QuizService;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
 @Autowired 
 private QuizService quizService ;
 Logger logger = LoggerFactory.getLogger(QuizController.class);
    @PostMapping("/create")
    public ResponseEntity<Quiz>createQuiz(@RequestParam String category , @RequestParam String title , @RequestParam int numOfQuestions , @RequestParam String createdBy) {
         try {
            logger.info("Received request to create quiz with title: {}, category: {}, numOfQuestions: {}, createdBy: {}", title, category, numOfQuestions, createdBy);
            logger.info("Calling QuizService to create quiz...");
           return quizService.createQuiz(category, title, numOfQuestions, createdBy) ;
        } catch (Exception e) {
            logger.error("Error occurred while creating quiz", e);
            return ResponseEntity.status(500).build();
        }
        
    }
   
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        try {
            logger.info("Calling QuizService to get all quizzes...");
            return quizService.getAllQuizzes();
        } catch (Exception e) {
            logger.error("Error occurred while fetching all quizzes", e);
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/category")
    public ResponseEntity<List<Quiz>> getAllQuizzesByCategory(@RequestParam String category) {
        try {
            logger.info("Calling QuizService to get quizzes by category: {}", category);
            return quizService.getAllQuizzesByCategory(category);
        } catch (Exception e) {
            logger.error("Error occurred while fetching quizzes by category", e);
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/id")
    public ResponseEntity<List<Question>> getQuizById(@RequestParam @NonNull String id) {
        try {
            logger.info("Calling QuizService to get quiz by id: {}", id);
            return quizService.getQuizById(id);
        } catch (Exception e) {
            logger.error("Error occurred while fetching quiz by id", e);
            return ResponseEntity.status(500).build();
        }
    }
}
