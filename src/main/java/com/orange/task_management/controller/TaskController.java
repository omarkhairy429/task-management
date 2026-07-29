package com.orange.task_management.controller;

import com.orange.task_management.model.Task;
import com.orange.task_management.model.User;
import com.orange.task_management.repository.TaskRepository;
import com.orange.task_management.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/v1/tasks")
    public List<Task> getAllTasks(Authentication auth) {
        System.out.println(auth.getName());
        return taskRepository.findByUserUsername(auth.getName());
    }

    @PostMapping("/api/v1/tasks")
    public Task createTask(@RequestBody Task task, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Problem finding current user"));
        task.setUser(currentUser);
        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("/api/v1/tasks/{id}")
    public void deleteTask(Authentication auth, @PathVariable Long id) {
        System.out.println("Delete Task Controller");
        Task taskToDelete = taskRepository.findByIdAndUserUsername(id, auth.getName());
        if (taskToDelete != null) {
            taskRepository.delete(taskToDelete);
        }
    }

    @PutMapping("/api/v1/tasks/{id}")
    public Task updateTask(@RequestBody Task task,Authentication auth, @PathVariable Long id) {
        System.out.println("Update Task Controller");
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Problem finding current user"));

        Task taskToUpdate = taskRepository.findByIdAndUserUsername(id, auth.getName());
        task.setUser(currentUser);
        if (taskToUpdate != null) {
            taskRepository.save(task);
        }
        return task;
    }



}
