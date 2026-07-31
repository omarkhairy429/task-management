package com.orange.task_management.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest
public class AuthSteps {
    private Response response;

    @Given("the auth base URI is {string}")
    public void setAuthBaseUri(String uri) {
        RestAssured.baseURI = uri;
    }

    @When("I make a POST request to {string} with the following user data:")
    public void makePostRequest(String endpoint, String payload) {
        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(endpoint);
    }

    @Then("the auth API response status should be {int}")
    public void verifyAuthStatus(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode());
    }

    @Then("the response body should return a valid JWT token")
    public void verifyToken() {
        String token = response.getBody().asString();
        assertNotNull(token, "Token should not be null");
        assertFalse(token.contains("Invalid Data"), "Registration failed with Invalid Data");
        assertFalse(token.contains("You are not allowed"), "Login failed with invalid credentials");
    }
}