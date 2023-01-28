Feature: Test POST requests to add/update pets with different options

  Scenario Outline:Verify user can add a pet with POST request
    Given add pet payload with "<name>" "<category name>" "<tag>"
    When user performs "AddPet"
    Then user verify that POST API call is successful and status code 200 is returned
    And id of the pet is the same as posted id

    Examples:
      | name   | category name | tag  |
      | Boss   | Dogs          | tag1 |
      | Fluffy | Cats          | tag2 |

  Scenario:Verify user can update a pet with PUT request
    Given add pet payload with "Rocky" "turtle" "tag5"
    When user performs "AddPet"
    Then user verify that POST API call is successful and status code 200 is returned
    And id of the pet is the same as posted id
    When user performs "UpdatePet"
    And updates pet with "Johnny" "fish" "tag7"
    And the following values are updated
    |name|Johnny|
    |categoryName|fish|
    |tag         |tag7|


  Scenario:Verify user can update a pet with form data with POST request
    Given add pet payload with "Memus" "possum" "tag9"
    When user performs "AddPet"
    Then user verify that POST API call is successful and status code 200 is returned
    When user performs "FormUpdatePet"
    And updates pet with name "Ramu" and status "sold"