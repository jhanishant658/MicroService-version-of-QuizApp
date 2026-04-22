package QuizApp.MicroService.AttemptService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import QuizApp.MicroService.AttemptService.Response.QuizAnsResponse;

@FeignClient(name = "QuestionService")
public interface QuestionSercvice {
     @PostMapping("/api/questions/getMarks")
    public ResponseEntity<Integer> getMarks(@RequestBody QuizAnsResponse quizAnsRes) ;
}
