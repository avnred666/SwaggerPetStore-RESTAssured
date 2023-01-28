Feature: Test DELETE request to delete pets
  Scenario:Verify user can delete pets by id
    Given add pet payload with "Robby" "dinosaur" "tag1"
    When user performs "AddPet"
    Then user verify that POST API call is successful and status code 200 is returned
    When user performs "DeletePet"
    Then user verify that DELETE API call is successful and status code 200 is returned
    And message "Pet deleted" is returned