package QuizApp.MicroService.AttemptService.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "attempts")
@Data
public class Attempt {
    @Id
    private String id;
    private String userId;
    private String quizId;
    private int totalscore;
    private int totalMarks;
}
