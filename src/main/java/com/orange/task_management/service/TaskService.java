package com.orange.task_management.service;
import com.orange.task_management.dto.TaskRequest;
import com.orange.task_management.dto.TaskResponse;
import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import com.orange.task_management.model.Task;
import com.orange.task_management.model.User;
import com.orange.task_management.repository.TaskRepository;
import com.orange.task_management.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /***************************** Getting All Tasks *****************************/
    public List<TaskResponse> getAllTasks(Authentication auth, Priority priority, Status status) {
        List<Task> tasks;

        if (priority != null && status != null) {
            tasks = taskRepository.findByUserUsernameAndPriorityAndStatus(auth.getName(), priority, status);
        } else if (priority != null) {
            tasks = taskRepository.findByUserUsernameAndPriority(auth.getName(), priority);
        } else if (status != null) {
            tasks = taskRepository.findByUserUsernameAndStatus(auth.getName(), status);
        } else {
            tasks = taskRepository.findByUserUsername(auth.getName());
        }
        return tasks
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }



    /***************************** Creating a task *****************************/
    public TaskResponse createTask(TaskRequest request, Authentication auth) {
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Problem finding current user"));

        Task task = Task.builder()
                .name(request.name())
                .status(request.status() != null ? request.status() : Status.TODO)
                .priority(request.priority() != null ? request.priority() : Priority.MEDIUM)
                .user(currentUser)
                .build();

        taskRepository.save(task);
        return TaskResponse.fromEntity(task);
    }

    /***************************** Deleting a task *****************************/
    public void deleteTask(Authentication auth,Long id) {
        System.out.println("Delete Task Controller");
        Task taskToDelete = taskRepository.findByIdAndUserUsername(id, auth.getName());
        if (taskToDelete != null) {
            taskRepository.delete(taskToDelete);
        }
    }


    /***************************** Updating a task *****************************/
    public TaskResponse updateTask(TaskRequest request,Authentication auth,Long id) {
        System.out.println("Update Task Controller");
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Problem finding current user"));

        Task taskToUpdate = taskRepository.findByIdAndUserUsername(id, auth.getName());
        if (taskToUpdate == null) {
            throw new RuntimeException("Task not found");
        }
        taskToUpdate.setName(request.name());
        taskToUpdate.setStatus(request.status() != null ? request.status(): Status.TODO);
        taskToUpdate.setPriority(request.priority() != null? request.priority(): Priority.MEDIUM);
        taskToUpdate.setUser(currentUser);
        taskRepository.save(taskToUpdate);

        return TaskResponse.fromEntity(taskToUpdate);
    }
}
