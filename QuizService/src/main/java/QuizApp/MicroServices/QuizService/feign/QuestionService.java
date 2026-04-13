package QuizApp.MicroServices.QuizService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import QuizApp.MicroServices.QuizService.Model.Question;

@FeignClient(name = "QuestionService")
public interface QuestionService {
      @PostMapping("/api/questions/getQuestionforQuiz")
    public ResponseEntity<List<String>> getQuestionForQuiz(@RequestParam String categories , @RequestParam int numOfQuestions) ;
    @PostMapping("/api/questions/id")
    public ResponseEntity<Question> getQuestionById(@RequestParam String id) ;
}
