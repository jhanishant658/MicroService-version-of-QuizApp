package QuizApp.MicroServices.QuizService.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import QuizApp.MicroServices.QuizService.Model.Question;

@Component
public class QuestionServiceFallback implements QuestionService {

    @Override
    public ResponseEntity<List<String>> getQuestionForQuiz(String category, int numOfQuestions) {
        System.out.println("🔥 Feign fallback hit: getQuestionForQuiz");

        // Return an empty list of question IDs as a fallback
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Question> getQuestionById(String id) {
        System.out.println("🔥 Feign fallback hit: getQuestionById");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}