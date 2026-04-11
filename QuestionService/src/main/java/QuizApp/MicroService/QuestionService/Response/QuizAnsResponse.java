package QuizApp.MicroService.QuestionService.Response;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnsResponse {
    private String questionId;
    private String selectedAnswer;
}

