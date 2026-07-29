package com.orange.task_management.controller;

import com.orange.task_management.model.Task;
import com.orange.task_management.repository.TaskRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/v1/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }



}
