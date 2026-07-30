package com.orange.task_management.repository;

import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import com.orange.task_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByUserUsername(String username);
    public Task findByIdAndUserUsername(Long id, String username);
    public List<Task> findByUserUsernameAndPriorityAndStatus(String username, Priority priority, Status status);
    public List<Task> findByUserUsernameAndPriority(String username, Priority priority);
    public List<Task> findByUserUsernameAndStatus(String username, Status status);
}
