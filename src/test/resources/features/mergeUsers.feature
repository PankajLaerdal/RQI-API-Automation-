@merge
Feature: Merge User API

  Scenario Outline: Validate merge user API
    Given the base merge URI is set
    And I set merge authorization token as "valid"
    And I prepare merge user payload "<payloadType>"
    When I send POST request to merge user API
    Then the response merge status code should be <statusCode>
    And I validate merge response "<validationType>"

  Examples:
    | tokenType | payloadType | statusCode | validationType |
    | valid     | valid       | 202        | success        |
    | invalid   | valid       | 401        | error          |
    | missing   | valid       | 401        | error          |
    | valid     | invalid     | 400        | error          |