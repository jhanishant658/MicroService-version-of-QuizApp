package QuizApp.MicroService.AttemptService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import QuizApp.MicroService.AttemptService.Entity.Attempt;
import QuizApp.MicroService.AttemptService.Request.SubmitQuizRequest;
import QuizApp.MicroService.AttemptService.Service.AttemptService;

@RestController
@RequestMapping("/api/attempts")
public class AttemptController {
    @Autowired
    private AttemptService attemptService;
  @PostMapping("/submit")
  public String submitAttempt(@RequestBody SubmitQuizRequest request) {
    return attemptService.calculateMarks(request.getQuizId(), request.getUserId(), request.getAnswers());
  }
// done avg response time of api on 1000 request is 260 ms 
// converted avg response time to 88 ms by adding index on userId and quizId in attempt collection and also by using projection to return only required fields in leaderboard api
  @GetMapping("/leaderboard")
  public List<Attempt> getLeaderBoard(@RequestParam String quizId) {
    return attemptService.getLeaderBoard(quizId);
  }
  // done avg response time of api on 1000 request for this route is 88 ms
  @GetMapping("/userAttempts")
  public List<Attempt> getUserAttempts(@RequestParam String userId) {
    return attemptService.getAttemptsByUserId(userId);
  }
  // done avg response time of api on 1000 request for this route is 110 ms
    @GetMapping("/attemptDetails")
    public Attempt getAttemptDetails(@RequestParam @NonNull String attemptId) {
        return attemptService.getAttemptById(attemptId);
    }
}
