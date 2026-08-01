# Task Management App

Full-stack task management application with user authentication, task CRUD operations, and filtering by status and priority.
----------
# Tech Stack
# Backend: Java 21, Spring Boot, Spring Security (JWT), Spring Data JPA, Lombok

# Frontend: Angular 19, TypeScript

# Database: PostgreSQL 17

# Testing: Cucumber (BDD)

# DevOps: Docker, Docker Compose, GitHub Actions
-------------
# API Endpoints
# Authentication
# POST /api/v1/auth/register — Register a new user

# POST /api/v1/auth/login — Authenticate and receive JWT token

# Tasks (Requires Authentication)
# GET /api/v1/tasks — Fetch user's tasks (Optional query params: priority, status)

# POST /api/v1/tasks — Create a new task

# PUT /api/v1/tasks/{id} — Update an existing task by ID

# DELETE /api/v1/tasks/{id} — Delete a task by ID

# Note: Each user can only view, edit, or delete their own tasks.

-------------
# Project Structure

src/main/java/com/orange/task_management/
├── config/
├── controller/
├── dto/
├── enums/
├── exception/
├── model/
├── repository/
├── security/
└── service/
-------------
# Design Patterns Used
# DTO Pattern: Separates database entities from API request/response payloads (TaskRequest, TaskResponse, UserRequest, UserLogin).

# Builder Pattern: Used via Lombok @Builder on models/DTOs for object construction.

# Global Exception Handler: Custom exception handling across controllers using @RestControllerAdvice.

# Layered Architecture: Clear division across Controller, Service, and Repository layers.
-------------
# Database Schema (ERD)
# Entities:

# User: id (PK), username, email, password

# Task: id (PK), name, status (Enum), priority (Enum), user_id (FK)

Relationship: User (1) — (N) Task
-------------

# How to Run
# Using Docker Compose 

# Create a .env file in the root folder:
# JWT_SECRET=your_jwt_secret_key_here

# Run:

# docker-compose up --build

# Frontend: http://localhost:3000

# Backend API: http://localhost:8080

# Database: localhost:5432
# A basic GitHub Actions workflow runs on every push or pull request to main and develop branches:

# Sets up JDK 21.

# Builds the Spring Boot application and runs Cucumber BDD tests against a temporary test database.

# Builds the Angular application.
