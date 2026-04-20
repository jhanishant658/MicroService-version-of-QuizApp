package QuizApp.MicroService.UserService.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import QuizApp.MicroService.UserService.Entity.User;
import QuizApp.MicroService.UserService.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
        @PostMapping("/login")
        public String Login(@RequestBody User user) {
       logger.info("Login attempt for user {}", user.getEmail());
            return userService.login(user.getEmail(), user.getPassword() );
        }

        @PostMapping("/register")
        public String register(@RequestBody @NonNull User user) {
            logger.info("Registration attempt for user {}", user.getEmail());
            return userService.register(user);
        }
        
    
}