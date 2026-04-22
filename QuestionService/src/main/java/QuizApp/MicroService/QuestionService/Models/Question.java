package QuizApp.MicroService.QuestionService.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Document(collection = "questions")

@Data
public class Question {
 @Id
 @Indexed(unique = true)
  private String id ; 
  @NotBlank(message = "Level cannot be blank")
  private String level ; 
  @Indexed
  @NotBlank(message = "Category cannot be blank")
  private String category ; 
  @NotBlank(message = "Question text cannot be blank")
  private String questionText ; 
  @Size(min = 2, max = 4 ,  message = "At least two options are required")
  private String[] options ; 
  @NotBlank(message = "Correct answer text cannot be blank")
  private String correctAnswer ;    
}