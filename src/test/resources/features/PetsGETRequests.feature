Feature: Test GET requests to get pets with different options
Scenario:Verify user can find pets by available status
  When user performs pets "findByStatus" using "available" parameter
  Then user verify that API call is successful and status code 200 is returned
  And "status" in response body is "available"

Scenario: Verify user can find pets by their tags
    When user performs pets "findByTags" using "tag1" parameter
    Then user verify that API call is successful and status code 200 is returned
    And "tags" in response body is "tag1"

Scenario: Verify user can find pets by their id
    When user finds pets using "findByID" using "1" parameter
    Then user verify that API call is successful and status code 200 is returned
    And "id" in response is 1