package QuizApp.MicroService.QuestionService.QuestionServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import QuizApp.MicroService.QuestionService.Models.Question;
import QuizApp.MicroService.QuestionService.Repositories.QuestionRepository;
import QuizApp.MicroService.QuestionService.Services.QuestionService;
import jakarta.validation.ConstraintViolationException;


@ExtendWith(MockitoExtension.class)
public class AddQuestionSuccess {

    @Mock
    private QuestionRepository questionRepository ;
    @InjectMocks
    private QuestionService questionService ;
    @Test
    public void testAddQuestionSuccess(){
       Question question = new Question() ;
        question.setId("1");
         question.setCategory("Science");
         question.setLevel("Easy");
         question.setQuestionText("What is the capital of India?");
         question.setOptions(new String[]{"New Delhi", "Mumbai", "Bangalore", "Chennai"});
         question.setCorrectAnswer("New Delhi");
when(questionRepository.save(question))
        .thenReturn(question);
       
        assertEquals(question, questionService.addQuestion(question).getBody() );
        verify(questionRepository).save(question);
    }
    @Test
    public void testAddQuestionFailure(){
         Question question = new Question();
            question.setId("1");
            question.setCategory("Science");
            question.setLevel("");
            question.setQuestionText("What is the capital of India?");
            question.setOptions(new String[]{"New Delhi", "Mumbai", "Bangalore", "Chennai"});
            question.setCorrectAnswer("New Delhi");
            when(questionRepository.save(question)).thenReturn(question);
            
        assertThrows(ConstraintViolationException.class, () -> questionService.addQuestion(question));
          verify(questionRepository , never()).save(question);
    }
}