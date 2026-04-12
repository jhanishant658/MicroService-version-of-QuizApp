package QuizApp.MicroServices.QuizService.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import QuizApp.MicroServices.QuizService.Model.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz,String> {

   List<Quiz> findByCategory(String category);

    
} 