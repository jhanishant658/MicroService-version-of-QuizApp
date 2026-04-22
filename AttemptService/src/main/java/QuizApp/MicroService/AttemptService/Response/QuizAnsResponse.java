package QuizApp.MicroService.AttemptService.Response;

import lombok.Data;

@Data
public class QuizAnsResponse {
    private String questionId;
    private String selectedAnswer;
}