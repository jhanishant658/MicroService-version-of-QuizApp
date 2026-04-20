package QuizApp.MicroService.UserService.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import QuizApp.MicroService.UserService.Entity.User;
import QuizApp.MicroService.UserService.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
        @PostMapping("/login")
        public String getMethodName(@RequestBody User user) {
            return userService.login(user.getEmail(), user.getPassword() , user.getRole());
        }

        @PostMapping("/register")
        public String register(@RequestBody @NonNull User user) {
            return userService.register(user);
        }
        
    
}