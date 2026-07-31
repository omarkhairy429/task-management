package com.orange.task_management.service;
import com.orange.task_management.model.User;
import com.orange.task_management.repository.UserRepository;
import com.orange.task_management.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(User loginUser) {
        User user = userRepository.findByUsername(loginUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        else {
            return "You are not allowed";
        }
    }

    public String register(User registerUser) {
        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        User user = userRepository.save(registerUser);

        if (user == null) {
            return  "Invalid Data";
        }
        else {
            return jwtService.generateToken(user.getUsername());
        }
    }
}
