package QuizApp.MicroService.AttemptService.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "attempts")
@Data
public class Attempt {
    @Id
    @Indexed(unique = true)
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String quizId;
    
    private int totalscore;
    private int totalMarks;
}
