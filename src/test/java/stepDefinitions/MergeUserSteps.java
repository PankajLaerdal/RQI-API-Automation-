package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.UserMergePayload;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class MergeUserSteps{

   
    ThreadLocal<Response> response = new ThreadLocal<>();

    String token;
    Map<String, Object> payload;

   
    @Given("the base merge URI is set")
    public void setBaseURI() {
        BaseTest.setup();
    }

   
    @And("I set merge authorization token as {string}")
    public void setAuthorizationToken(String tokenType) {

        switch (tokenType.toLowerCase()) {
            case "valid":
            	token = "Bearer " + ConfigReader.getProperty("token");
                break;

            case "invalid":
                token = "Bearer invalid_token";
                break;

            case "missing":
                token = null;
                break;

            default:
                throw new IllegalArgumentException("Invalid token type");
        }
    }
  
  
    @And("I prepare merge user payload {string}")
    public void preparePayload(String payloadType) {

        switch (payloadType.toLowerCase()) {
            case "valid":
                payload = UserMergePayload.getPayload();
                break;

            case "invalid":
                payload = new HashMap<>(); // empty/invalid payload
                break;

            default:
                throw new IllegalArgumentException("Invalid payload type");
        }
    }

   
    @When("I send POST request to merge user API")
    public void sendPostRequest() {

        RequestSpecification request = given()
                .header("Content-Type", "application/json").header("Accept", "application/json")
                .log().all();

        if (token != null) {
            request.header("Authorization", token);
        }

        response.set(
                request
                        .body(payload)
                .when()
                        .post("/organizations/11021618/users/2100562358/merge")
                .then()
                        .log().all()
                        .extract().response()
        );
    }

  
    @Then("the response merge status code should be {int}")
    public void validatemergeStatusCode(int expectedStatusCode) {

        int actual = response.get().getStatusCode();

        System.out.println("Expected: " + expectedStatusCode);
        System.out.println("Actual: " + actual);
        System.out.println("Response Body: " + response.get().asString());

        assertEquals(expectedStatusCode, actual);
    }

    
    @And("I validate merge response {string}")
    public void validateResponse(String validationType) {

        Response res = response.get();

        
        System.out.println("STATUS: " + res.getStatusCode());
        System.out.println("BODY: " + res.asString());
        System.out.println("CONTENT TYPE: " + res.getContentType());

        
        if (res.getContentType() == null || !res.getContentType().contains("json")) {
            throw new RuntimeException("❌ Response is NOT JSON → " + res.asString());
        }

      
        switch (validationType.toLowerCase()) {

            case "success":
                assertEquals("Merge Successful",
                        res.jsonPath().getString("message"));
                break;

            case "error":
                assertNotNull(
                        res.jsonPath().getString("error")
                );
                break;

            default:
                throw new IllegalArgumentException("Invalid validation type");
                
        }
        
    }
    
}