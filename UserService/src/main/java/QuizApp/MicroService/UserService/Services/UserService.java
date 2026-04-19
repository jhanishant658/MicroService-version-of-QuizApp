package QuizApp.MicroService.UserService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import QuizApp.MicroService.UserService.Configurations.JwtUtil;
import QuizApp.MicroService.UserService.Entity.User;
import QuizApp.MicroService.UserService.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    public String register(@NonNull User user) {
        String pass = user.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(pass));
        userRepository.save(user);
        return "User registered successfully";
    }
    public String login(String email , String password) {
        try {
          PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          if(userRepository.findByEmail(email).isPresent() && passwordEncoder.matches(password, userRepository.findByEmail(email).get().getPassword())){
            return jwtUtil.generateToken(email);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Invalid credentials";
    }

}
