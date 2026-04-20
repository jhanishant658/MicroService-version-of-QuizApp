package QuizApp.MicroService.UserService.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public String register(@NonNull User user) {
      if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            logger.warn("Attempt to register with existing email: {}", user.getEmail());
            return "Email already in use";
        }
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User {} registered successfully", user.getEmail());
        return "User registered successfully";
    }
    public String login(String email , String password) {
        try {
          User user = userRepository.findByEmail(email).orElse(null);
          if(user!=null && passwordEncoder.matches(password, user.getPassword())){
            logger.info("User {} logged in successfully", email);
            return jwtUtil.generateToken(email ,user.getRole() );
          }
          logger.warn("Failed login attempt for user {}", email);
        } catch (Exception e) {
            logger.error("Error occurred while logging in user {}", email, e);
        }
        return "Invalid credentials";
    }

}
