package com.orange.task_management.dto;

import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;

public record TaskRequest (
    String name,
    Status status,
    Priority priority
) {}
