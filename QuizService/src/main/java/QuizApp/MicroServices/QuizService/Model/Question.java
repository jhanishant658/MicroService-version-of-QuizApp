package QuizApp.MicroServices.QuizService.Model;

import lombok.Data;

@Data
public class Question {
     private String id ; 
  
  private String level ; 
  
  private String category ; 
  
  private String questionText ; 
  private String[] options ; 
}
