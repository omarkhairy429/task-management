package com.orange.task_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLogin(
        @NotBlank
        @Size(min = 3, max = 100)
        String username,

        @NotBlank
        @Size(min = 8, max = 255)
        String password
) {
}
