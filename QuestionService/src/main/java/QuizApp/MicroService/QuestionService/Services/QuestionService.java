package QuizApp.MicroService.QuestionService.Services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import QuizApp.MicroService.QuestionService.Models.Question;
import QuizApp.MicroService.QuestionService.Repositories.QuestionRepository;
import QuizApp.MicroService.QuestionService.Repositories.QuestionRepository.QuestionIdOnly;
import QuizApp.MicroService.QuestionService.Response.QuizAnsResponse;

@Service
public class QuestionService {
    @Autowired 
    private QuestionRepository questionRepository ;
    public  ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<List<Question>> getQuestionByCategory(String category){
        return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
    }
    public ResponseEntity<Question> addQuestion(Question question){
        return new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED);
    }
    public ResponseEntity<List<String>> getQuestionForQuiz(String category, int numOfQuestions) {

    List<QuestionIdOnly> questions = questionRepository
            .findRandomQuestionIds(category, numOfQuestions);

    if (questions.size() < numOfQuestions) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<String> questionIds = questions.stream()
            .map(QuestionIdOnly::getId)
            .toList();

    return new ResponseEntity<>(questionIds, HttpStatus.OK);
}
    public ResponseEntity<Question> getQuestionById(String id) {
       return new ResponseEntity<>(questionRepository.findById(id).orElse(null), HttpStatus.OK);
    }
    
    public ResponseEntity<Integer> getMarks(List<QuizAnsResponse> answers) {
       List<String> ids = answers.stream()
            .map(QuizAnsResponse::getQuestionId)
            .toList();

    // 2. ek hi baar DB hit
    List<Question> questions = questionRepository.findAllById(ids);

    // 3. map bana (fast lookup)
    Map<String, String> correctMap = questions.stream()
            .collect(Collectors.toMap(
                    Question::getId,
                    Question::getCorrectAnswer
            ));

    // 4. score calculate
    int score = 0;

    for (QuizAnsResponse ans : answers) {
        String correct = correctMap.get(ans.getQuestionId());

        if (correct != null && correct.equals(ans.getSelectedAnswer())) {
            score++;
        }
    }

         return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
