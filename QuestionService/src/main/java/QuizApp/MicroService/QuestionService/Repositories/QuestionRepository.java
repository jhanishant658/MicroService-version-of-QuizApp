package QuizApp.MicroService.QuestionService.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import QuizApp.MicroService.QuestionService.Models.Question;
@Repository
public interface QuestionRepository extends MongoRepository<Question,String> {

    List<Question> findByCategory(String category);
    
}
