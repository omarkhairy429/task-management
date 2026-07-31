package com.orange.task_management.dto;

import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest (
        @NotBlank(message = "Task name is required")
        @Size(min = 3, max = 50, message = "Task Name must be between 3 and 50 characters")
        String name,
        Status status,
        Priority priority
) {}
