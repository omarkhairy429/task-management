package com.orange.task_management.service;
import com.orange.task_management.dto.UserLogin;
import com.orange.task_management.dto.UserRequest;
import com.orange.task_management.model.User;
import com.orange.task_management.repository.UserRepository;
import com.orange.task_management.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;



    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(UserLogin loginUser) {
        User user = userRepository.findByUsername(loginUser.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginUser.password(), user.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public String register(UserRequest registerUser) {

        if (userRepository.existsByUsername(registerUser.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(registerUser.username())
                .email(registerUser.email())
                .password(passwordEncoder.encode(registerUser.password()))
                .build();

        userRepository.save(user);
        return jwtService.generateToken(user.getUsername());

    }
}
