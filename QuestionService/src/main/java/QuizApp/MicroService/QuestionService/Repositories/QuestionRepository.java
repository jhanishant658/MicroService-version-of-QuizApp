package QuizApp.MicroService.QuestionService.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Aggregation;
import QuizApp.MicroService.QuestionService.Models.Question;
@Repository
public interface QuestionRepository extends MongoRepository<Question,String> {
     List<Question> findAllById(Iterable<String> ids);
    List<Question> findByCategory(String category);
    @Aggregation(pipeline = {
    "{ $match: { category: ?0 } }",
    "{ $sample: { size: ?1 } }",
    "{ $project: { _id: 1 } }"
})
List<QuestionIdOnly> findRandomQuestionIds(String category, int size);

    interface QuestionIdOnly {
        String getId();
    }
    
}
