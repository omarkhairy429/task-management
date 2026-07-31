Feature: Task Management APIs

  Background:
    Given the task base URI is "http://localhost:8080"
    And I am logged in with username "testuser" and password "password123"

  Scenario: Create a new Task
    When I submit a POST request to "/api/v1/tasks" with task details:
      """
      {
        "name": "Design Database",
        "status": "TODO",
        "priority": "HIGH"
      }
      """
    Then the task API response status should be 200
    And the created task should have the name "Design Database"

  Scenario: Get all Tasks
    When I submit a GET request to "/api/v1/tasks"
    Then the task API response status should be 200

  Scenario: Update an existing Task
    When I submit a PUT request to update the created task with details:
      """
      {
        "name": "Design Database Updated",
        "status": "IN_PROGRESS",
        "priority": "MEDIUM"
      }
      """
    Then the task API response status should be 200

  Scenario: Delete a Task
    When I submit a DELETE request to delete the created task
    Then the task API response status should be 200