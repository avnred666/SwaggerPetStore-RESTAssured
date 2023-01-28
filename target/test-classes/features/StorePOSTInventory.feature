Feature: Place and order for a pet
  Scenario: Place a new order in the store
    Given add order payload
    When user places an order for a pet
    Then user verify that status code 200 is returned
    And user verify that following values are returned
    |status|approved|
    |complete|true  |