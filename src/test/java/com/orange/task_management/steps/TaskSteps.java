package com.orange.task_management.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskSteps {
    private Response response;
    private static String jwtToken;
    private static Long createdTaskId;

    @Given("the task base URI is {string}")
    public void setTaskBaseUri(String uri) {
        RestAssured.baseURI = uri;
    }

    @Given("I am logged in with username {string} and password {string}")
    public void loginAndGetToken(String username, String password) {
        String payload = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", username, password);

        Response loginResponse = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/api/v1/auth/login");

        jwtToken = loginResponse.getBody().asString();
    }

    @When("I submit a POST request to {string} with task details:")
    public void submitPostRequest(String endpoint, String payload) {
        response = given()
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .post(endpoint);

        if (response.getStatusCode() == 200) {
            createdTaskId = response.jsonPath().getLong("id");
        }
    }

    @When("I submit a GET request to {string}")
    public void submitGetRequest(String endpoint) {
        response = given()
                .header("Authorization", "Bearer " + jwtToken)
                .get(endpoint);
    }


    @When("I submit a PUT request to update the created task with details:")
    public void submitPutRequestForCreatedTask(String payload) {
        response = given()
                .header("Authorization", "Bearer " + jwtToken)
                .header("Content-Type", "application/json")
                .body(payload)
                .put("/api/v1/tasks/" + createdTaskId);
    }


    @When("I submit a DELETE request to delete the created task")
    public void submitDeleteRequestForCreatedTask() {
        response = given()
                .header("Authorization", "Bearer " + jwtToken)
                .delete("/api/v1/tasks/" + createdTaskId);
    }

    @Then("the task API response status should be {int}")
    public void verifyTaskStatus(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode());
    }

    @Then("the created task should have the name {string}")
    public void verifyTaskName(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        assertEquals(expectedName, actualName);
    }
}