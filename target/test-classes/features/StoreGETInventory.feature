Feature: Returns pet inventories by status
  Scenario: Returns a map of status codes to quantities
    When user submits GET request for inventory status
    Then user verify that store GET API call is successful and status code 200 is returned
    And values are updated as follows:
    |approved|65|
    |placed  |100|
    |delivered|50|

