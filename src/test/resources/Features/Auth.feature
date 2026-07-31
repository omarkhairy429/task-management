Feature: Authentication APIs

  @Valid
  Scenario: User Registration
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {
        "username": "testuser",
        "email": "testUser@gmail.com",
        "password": "password123"
      }
      """
    Then the auth API response status should be 200
    And the response body should return a valid JWT token

  @Valid
  Scenario: User Login
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/login" with the following user data:
      """
      {
        "username": "testuser",
        "password": "password123"
      }
      """
    Then the auth API response status should be 200
    And the response body should return a valid JWT token


  @Invalid
  Scenario: User Login with wrong password
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/login" with the following user data:
      """
      {
        "username": "testuser",
        "password": "password123456"
      }
      """
    Then the auth API response status should be 404

  @Invalid
  Scenario: User Login with wrong username
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/login" with the following user data:
      """
      {
        "username": "Omar123",
        "password": "password123456"
      }
      """
    Then the auth API response status should be 404

  @Invalid
  Scenario: User Sign in with short username
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {
        "username": "Om",
        "email": "om@gmail.com",
        "password": "password123456"
      }
      """
    Then the auth API response status should be 400


  @Invalid
  Scenario: User Sign in with short password
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {
        "username": "OmarKhairyFayed",
        "email": "om@gmail.com",
        "password": "pa"
      }
      """
    Then the auth API response status should be 400

  @Invalid
  Scenario: User Sign in with wrong email format
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {
        "username": "OmarKhairyFayed",
        "email": "om.gmail.com",
        "password": "pa"
      }
      """
    Then the auth API response status should be 400

  @Invalid
  Scenario: User Sign in with empty objeect
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {

      }
      """
    Then the auth API response status should be 400

  @Valid
  Scenario: Sign in with an existing user
    Given the auth base URI is "http://localhost:8080"
    When I make a POST request to "/api/v1/auth/register" with the following user data:
      """
      {
        "username": "testuser",
        "email": "testUser@gmail.com",
        "password": "password123"
      }
      """
    Then the auth API response status should be 404