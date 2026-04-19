package QuizApp.MicroService.UserService.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(
    collection = "users"
)

@Data
public class User {

    @Id
    private String id;

    private String name; // optional

    @Indexed(unique = true)
    private String email;

    private String password;

    private String role; // store like ROLE_USER
}