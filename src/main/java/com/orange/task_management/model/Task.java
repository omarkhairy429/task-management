package com.orange.task_management.model;

import com.orange.task_management.enums.Priority;
import com.orange.task_management.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")

public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'TODO'")
    @Enumerated(EnumType.STRING)
    private Status status = Status.TODO;

    @Builder.Default
    @Column(name = "priority", nullable = false, columnDefinition = "varchar(255) default 'MEDIUM'")
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
