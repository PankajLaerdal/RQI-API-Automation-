/*package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;

import static org.junit.Assert.*;

public class CourseSteps extends BaseTest {

    Response response;

    @Given("the base URI is set")
    public void the_base_uri_is_set() {
        setup();
    }

    @Given("I set authorization token")
    public void i_set_authorization_token() {
        request.header("Authorization", "Bearer " + ConfigReader.get("token"));
    }

    @When("I send GET request to course listing API")
    public void i_send_get_request() {
        response = request
                .log().all()
                .get(ConfigReader.get("courseEndpoint"));

        response.then().log().all();
    }

    @Then("the response status code should be 200")
    public void validate_status_code() {
        assertEquals(200, response.getStatusCode());
    }

    @Then("the response should contain course list")
    public void validate_course_list() {
        assertTrue(response.getBody().asString().contains("courses"));
    }
}*/