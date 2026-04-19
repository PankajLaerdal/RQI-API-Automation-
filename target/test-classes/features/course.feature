@course
Feature: Course Listing API

  Scenario Outline: Validate course listing API with different conditions
    Given the base URI is set
    And I set authorization token as "<tokenType>"
    And I set endpoint as "<endpointType>"
    When I send GET request to course listing API
    Then the response status code should be <statusCode>
    And I validate response based on "<validationType>"

  Examples:
    | tokenType | endpointType | statusCode | validationType |
    | valid     | valid        | 200        | success        |
    | invalid   | valid        | 401        | error          |
    | missing   | valid        | 401        | error          |
    | expired   | valid        | 401        | error          |
    | valid     | invalid      | 404        | error          |