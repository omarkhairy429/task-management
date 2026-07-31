package com.orange.task_management.controller;

import com.orange.task_management.dto.UserLogin;
import com.orange.task_management.dto.UserRequest;
import com.orange.task_management.model.User;
import com.orange.task_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/login")
    public String login(@Valid @RequestBody UserLogin loginUser) {
        return authService.login(loginUser);
    }

    @PostMapping("/api/v1/auth/register")
    public String register(@Valid @RequestBody UserRequest registerUser) {
        return authService.register(registerUser);
    }
}
