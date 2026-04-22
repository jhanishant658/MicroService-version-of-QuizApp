package QuizApp.MicroService.AttemptService.Request;

import java.util.List;

import QuizApp.MicroService.AttemptService.Response.QuizAnsResponse;
import lombok.Data;

@Data
public class SubmitQuizRequest {
    private String userId;
    private String quizId;
    private List<QuizAnsResponse> answers;
}
