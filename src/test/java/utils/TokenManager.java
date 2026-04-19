package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

	public class TokenManager {

	    private static String clientToken;
	    private static String userToken;

	    // CLIENT TOKEN 
	    public static String getClientToken() {
	        if (clientToken == null) {
	            generateClientToken();
	        }
	        return clientToken;
	    }

	    private static void generateClientToken() {

	        Response response =
	                given().log().all()
	                       
	                        .header("Content-Type", "application/x-www-form-urlencoded")
	                        .formParam("client_id", ConfigReader.getProperty("client.id"))
	                        .formParam("client_secret", ConfigReader.getProperty("client.secret"))
	                .when()
	                        .post(ConfigReader.getProperty("client.token.url"))
	                .then()
	                        .extract().response();

	        clientToken = response.jsonPath().getString("access_token");

	        System.out.println("Client Token Generated");
	    }

	    //USER TOKEN
	    public static String getUserToken() {
	        if (userToken == null) {
	            generateUserToken();
	        }
	        return userToken;
	    }

	    private static void generateUserToken() {

	        String clientToken = getClientToken();

	        Response response =
	                given()
	                        .header("Authorization", "Bearer " + clientToken)
	                        .header("Content-Type", "application/json")
	                        .body("{\n" +
	                                "  \"username\": \"" + ConfigReader.getProperty("username") + "\",\n" +
	                                "  \"password\": \"" + ConfigReader.getProperty("password") + "\"\n" +
	                                "}")
	                .when()
	                        .post(ConfigReader.getProperty("user.token.url"))
	                .then()
	                        .extract().response();

	        userToken = response.jsonPath().getString("access_token");

	        System.out.println("User Token Generated");
	        
	        
	    }
	   
	    
	}
