package com.orange.task_management.dto;

import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import com.orange.task_management.model.Task;

public record TaskResponse(
    String name,
    Status status,
    Priority priority
)
{
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getName(),
                task.getStatus(),
                task.getPriority()
        );
    }
}
