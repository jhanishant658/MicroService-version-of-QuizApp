package QuizApp.MicroService.AttemptService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import QuizApp.MicroService.AttemptService.Entity.Attempt;
import QuizApp.MicroService.AttemptService.Feign.QuestionSercvice;
import QuizApp.MicroService.AttemptService.Repository.AttemptRepository;

import QuizApp.MicroService.AttemptService.Response.QuizAnsResponse;
@Service

public class AttemptService {
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private QuestionSercvice questionSercvice;
    public String calculateMarks(String quizId , String userId , List<QuizAnsResponse> quizAnsRes) {
        int totalMarks = 0 ; 
        for(QuizAnsResponse ans : quizAnsRes) {
            totalMarks += questionSercvice.getMarks(ans).getBody();
        }
        Attempt attempt = new Attempt();
        attempt.setUserId(userId);
        attempt.setQuizId(quizId);
        attempt.setTotalscore(totalMarks);
        attempt.setTotalMarks(quizAnsRes.size());
        attemptRepository.save(attempt);
        return "Marks calculated successfully";
    }
    public List<Attempt> getAttemptsByUserId(String userId) {
        return attemptRepository.findByUserId(userId);
    }
    public List<Attempt> getLeaderBoard(String quizId){
        return attemptRepository.findByQuizIdOrderByTotalscoreDesc(quizId);
    }
    public Attempt getAttemptById(@NonNull String attemptId) {
        return attemptRepository.findById(attemptId).orElse(null);
    }
}
