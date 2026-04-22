package QuizApp.MicroService.AttemptService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/leaderboard")
  public List<Attempt> getLeaderBoard(@RequestParam String quizId) {
    return attemptService.getLeaderBoard(quizId);
  }
  @PostMapping("/userAttempts")
  public List<Attempt> getUserAttempts(@RequestParam String userId) {
    return attemptService.getAttemptsByUserId(userId);
  }
    @PostMapping("/attemptDetails")
    public Attempt getAttemptDetails(@RequestParam String attemptId) {
        return attemptService.getAttemptById(attemptId);
    }
}
