package stepDefinitions;

import base.BaseTest;
import utils.TestContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;

import static org.junit.Assert.*;



public class CourseStep extends BaseTest {

    Response response;
    String endpoint;  
    
    @Given("the base URI is set")
    public void the_base_uri_is_set() {
        setup();
    }

    @Given("I set authorization token as {string}")
    public void set_token(String tokenType) {

        switch (tokenType) {

            case "valid":
                request.header("Authorization", "Bearer " + ConfigReader.getProperty("token"));
                break;

            case "invalid":
                request.header("Authorization", "Bearer " + ConfigReader.getProperty("invalid.token"));
                break;

            case "expired":
                request.header("Authorization", "Bearer " + ConfigReader.getProperty("expired.token"));
                break;

            case "missing":
                break;
        }
    }

    
    @Given("I set endpoint as {string}")
    public void set_endpoint(String endpointType) {

    	if (endpointType.equalsIgnoreCase("valid")) {

            String orgId = ConfigReader.getProperty("course.org.id");
            String userId = ConfigReader.getProperty("course.user.id");

            TestContext.endpoint =
                    "/organizations/" + orgId +
                    "/users/" + userId +
                    "/merge/curricula";

        } else {
            TestContext.endpoint = "/v1/api/invalid-endpoint";
        }
    }

    @When("I send GET request to course listing API")
    public void i_send_get_request() {

      TestContext.response = request
                .log().all()
                .get(TestContext.endpoint);

        TestContext.response.then().log().all();
    }

    @Then("the response status code should be {int}")
    public void validate_status(int expectedStatus) {
    	assertEquals(expectedStatus, TestContext.response.getStatusCode());
    }

    @Then("I validate response based on {string}")
    public void validate_response(String type) {

        String body = TestContext.response.getBody().asString().toLowerCase();
        int status = TestContext.response.getStatusCode();

        switch (type) {

            case "success":
                assertTrue(body.length() > 0);
                break;

            case "Unauthenticated.":

                if (status == 401) {
                    assertTrue(body.contains("unauthorized") || body.contains("invalid"));
                } 
                else if (status == 404) {
                    assertTrue(body.contains("not found"));
                } 
                else if (status == 400) {
                    assertTrue(body.contains("invalid") || body.contains("bad request"));
                } 
                else {
                    assertTrue(body.length() > 0); // fallback
                }
                break;
        }
        
    }
}