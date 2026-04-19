package utils;

import io.restassured.response.Response;

import java.util.*;

import static org.junit.Assert.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseValidator {

    
    public static void validateAll(Response response) {

        
        validateSchema(response);

        assertEquals("Status code mismatch", 200, response.getStatusCode());

        
        assertTrue("Response time is too high",
                response.time() < 3000);

       
        assertTrue("Response is not JSON",
                response.getContentType().contains("json"));

      
        List<Map<String, Object>> courses =
                response.jsonPath().getList("courses");

        assertNotNull("Courses list is null", courses);
        assertTrue("Courses list is empty", courses.size() > 0);

        // Mandatory Fields Check
        for (Map<String, Object> course : courses) {

            assertNotNull("courseId is missing", course.get("courseId"));
            assertNotNull("courseName is missing", course.get("courseName"));
            assertNotNull("status is missing", course.get("status"));
        }

        //Data Type Validation
        assertTrue("courseId is not integer",
                courses.get(0).get("courseId") instanceof Integer);

        assertTrue("courseName is not string",
                courses.get(0).get("courseName") instanceof String);

        //Duplicate Course ID Check
        List<Integer> ids = new ArrayList<>();

        for (Map<String, Object> course : courses) {
            ids.add((Integer) course.get("courseId"));
        }

        Set<Integer> uniqueIds = new HashSet<>(ids);

        assertEquals("Duplicate course IDs found",
                ids.size(), uniqueIds.size());

        //Business Rule Validation (Status)
        for (Map<String, Object> course : courses) {

            String status = (String) course.get("status");

            assertTrue("Invalid course status",
                    status.equals("COMPLETED") ||
                    status.equals("IN_PROGRESS"));
        }

        //Business Rule Validation (Type)
        for (Map<String, Object> course : courses) {

            String type = (String) course.get("type");

            assertNotNull("Course type is null", type);
        }
    }

    //SCHEMA VALIDATION METHOD
    public static void validateSchema(Response response) {

        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("course-schema.json"));
    }
}