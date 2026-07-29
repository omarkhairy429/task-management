package com.orange.task_management;

import com.orange.task_management.model.Task;
import com.orange.task_management.model.User;
import com.orange.task_management.repository.TaskRepository;
import com.orange.task_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaskManagementApplication {
	private final PasswordEncoder passwordEncoder;

	public TaskManagementApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {

		SpringApplication.run(TaskManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner testDatabase(UserRepository userRepository,
	TaskRepository taskRepository) {
		return args -> {
			User user1 = new User().builder()
					.email("omar.fayed.5832@gmail.com")
					.username("omar")
					.password(passwordEncoder.encode("omar123123123"))
					.build();
			userRepository.save(user1);

			Task task1 = new Task().builder()
					.name("Learn German")
					.user(user1)
					.build();

			taskRepository.save(task1);
		};
	}

}
