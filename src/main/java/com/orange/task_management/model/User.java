package com.orange.task_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
