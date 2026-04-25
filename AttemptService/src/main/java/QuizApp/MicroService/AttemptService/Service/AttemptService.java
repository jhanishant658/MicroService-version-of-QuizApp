package QuizApp.MicroService.AttemptService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import QuizApp.MicroService.AttemptService.Entity.Attempt;
import QuizApp.MicroService.AttemptService.Feign.QuestionSercvice;
import QuizApp.MicroService.AttemptService.Repository.AttemptRepository;

import QuizApp.MicroService.AttemptService.Response.QuizAnsResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
@Service

public class AttemptService {
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private QuestionSercvice questionSercvice;
    private Logger logger = LoggerFactory.getLogger(AttemptService.class);
     
    @CircuitBreaker(name = "attemptService", fallbackMethod = "calculateMarksFallback")
    @Retry(name = "attemptRetryService" , fallbackMethod = "calculateMarksRetryFallback")
    public String calculateMarks(String quizId , String userId , List<QuizAnsResponse> quizAnsRes) {
        logger.info("Calculating marks for user: {} in service layer", userId);
        int totalMarks = questionSercvice.getMarks(quizAnsRes).getBody();
        Attempt attempt = new Attempt();
        attempt.setUserId(userId);
        attempt.setQuizId(quizId);
        attempt.setTotalscore(totalMarks);
        attempt.setTotalMarks(quizAnsRes.size());
       //attemptRepository.save(attempt);
        return "Marks calculated successfully";
    
    }
public String calculateMarksFallback(String quizId , String userId , List<QuizAnsResponse> quizAnsRes, Throwable t) {
 logger.error("Fallback method called for calculateMarks due to: {}", t.getMessage()); 
 return "Error occurred while calculating marks fall back occured for circuibreaker";
}
public String calculateMarksRetryFallback(String quizId , String userId , List<QuizAnsResponse> quizAnsRes, Throwable t) {
 logger.error("Retry fallback method called for calculateMarks due to: {}", t.getMessage()); 
 throw new RuntimeException("Error occurred while calculating marks retry fallback occured for retry");
}
    public List<Attempt> getAttemptsByUserId(String userId) {
        logger.info("Fetching attempts for user in service layer : {}", userId);
        return attemptRepository.findByUserId(userId);
    }
    public List<Attempt> getLeaderBoard(String quizId){
        logger.info("Fetching leaderboard for quiz: {}", quizId);
        return attemptRepository.findByQuizIdOrderByTotalscoreDesc(quizId);
    }
    public Attempt getAttemptById(@NonNull String attemptId) {
        try{
        logger.info("Fetching attempt by ID: {}", attemptId);
        Attempt attempt =  attemptRepository.findById(attemptId).orElse(null);
        if (attempt == null) {
            logger.warn("Attempt not found for ID in service layer: {}", attemptId);
        }
        return attempt;
    }
        catch(Exception e){
            logger.error("Error fetching attempt by ID: {} in service layer", attemptId, e);
            return null;
        }
    }
}
