package com.orange.task_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Username can't be empty")
        @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
        String username,

        @NotBlank(message = "Email can't be empty")
        @Email(message = "This is not a valid email address")
        String email,

        @NotBlank(message = "Password can't be empty")
        @Size(min = 8, max = 255, message = "Password must be between 3 and 100 characters")
        String password
) { }
