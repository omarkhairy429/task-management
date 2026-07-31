package com.orange.task_management.controller;
import com.orange.task_management.dto.TaskRequest;
import com.orange.task_management.dto.TaskResponse;
import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import com.orange.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/v1/tasks")
    public List<TaskResponse> getAllTasks
            (Authentication auth,
             @RequestParam(required = false) Priority priority,
             @RequestParam(required = false) Status status)
    {
        return taskService.getAllTasks(auth, priority, status);
    }

    @PostMapping("/api/v1/tasks")
    public TaskResponse createTask(@Valid @RequestBody  TaskRequest request, Authentication auth) {
        return taskService.createTask(request, auth);
    }


    @DeleteMapping("/api/v1/tasks/{id}")
    public void deleteTask(Authentication auth, @PathVariable Long id) {
       taskService.deleteTask(auth, id);
    }

    @PutMapping("/api/v1/tasks/{id}")
    public TaskResponse updateTask(@RequestBody TaskRequest request,Authentication auth, @PathVariable Long id) {
        return taskService.updateTask(request, auth, id);
    }


}
