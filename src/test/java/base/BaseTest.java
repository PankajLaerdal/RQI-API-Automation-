package base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public class BaseTest {

    protected static RequestSpecification request;

    public static void setup() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");
        request = RestAssured.given()
                .header("Content-Type", "application/json").header("Accept", "application/json");
    }
}