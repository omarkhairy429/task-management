package com.orange.task_management.controller;

import com.orange.task_management.model.User;
import com.orange.task_management.repository.UserRepository;
import com.orange.task_management.security.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtService jwtService;

    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository,
                          JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/v1/auth/login")
    public String login(@RequestBody User loginUser) {
        User user = userRepository.findByUsername(loginUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        else {
            return "You are not allowed";
        }

    }
}
