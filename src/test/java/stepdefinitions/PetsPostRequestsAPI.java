package stepdefinitions;

import testdata.BuildPetsData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet;
import pojo.Tag;
import utilities.ConfigProperties;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PetsPostRequestsAPI {
    ConfigProperties config = new ConfigProperties();
    RequestSpecification reqSpec,reqSpecDelete;
    Response response;
    BuildPetsData petsData = new BuildPetsData();
    Pet pet,responsePet,updatePet,responseUpdatedPet,formPet;
    String json;

    public PetsPostRequestsAPI() throws IOException {
    }

    @Given("add pet payload with {string} {string} {string}")
    public void add_pet_payload_with(String name, String categoryName, String tag1) throws JsonProcessingException {
        pet = petsData.buildPetJSON(0,name,categoryName,tag1);
        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(pet);
    }

    @When("user performs {string}")
    public void user_performs(String action) {
        reqSpec = given()
                .baseUri(config.getBaseURI())
                .body(json)
                .contentType("application/json").log().all();
        if(action.equalsIgnoreCase("AddPet")||action.equalsIgnoreCase("FormUpdatePet")){
            response = reqSpec.post("/pet");
        }
        else if(action.equalsIgnoreCase("UpdatePet")){
            response = reqSpec.put("/pet");
        }
        else if(action.equalsIgnoreCase("DeletePet")){
            reqSpecDelete = reqSpec.header("api_key",123);
            response = reqSpec.delete("/pet/"+pet.getId());
        }

    }

    @Then("user verify that POST API call is successful and status code {int} is returned")
    public void user_verify_that_post_api_call_is_successful_and_status_code_is_returned(Integer int1) {
       assertEquals(response.getStatusCode(),int1);
    }

    @And("id of the pet is the same as posted id")
    public void idOfThePetIsTheSameAsPostedId() throws JsonProcessingException {
        ObjectMapper mapper1 = new ObjectMapper();
        responsePet = mapper1.readValue(response.asString(),Pet.class);
        assertEquals(responsePet.getId(),pet.getId());
    }

    @And("updates pet with {string} {string} {string}")
    public void updatesPetWith(String arg0, String arg1, String arg2) throws JsonProcessingException {
        updatePet = petsData.buildPetJSON(pet.getId(),arg0,arg1,arg2);
        ObjectMapper mapper2 = new ObjectMapper();
        json = mapper2.writeValueAsString(updatePet);
        reqSpec = given()
                .baseUri(config.getBaseURI())
                .body(json)
                .contentType("application/json").log().all();
        response = reqSpec.post("/pet");
    }

    @And("^the following values are updated$")
    public void the_following_values_are_updated(DataTable dataTable) throws Throwable {
        boolean flag = false;
        ObjectMapper mapper3 = new ObjectMapper();
        responseUpdatedPet = mapper3.readValue(response.asString(),Pet.class);
        assertEquals(responsePet.getId(),pet.getId());
        Map<String, String> data = dataTable.asMap(String.class,String.class);

        assertEquals(data.get("name"),responseUpdatedPet.getName());
        assertEquals(data.get("categoryName"),responseUpdatedPet.getCategory().getName());
        for(Tag singleTag : responseUpdatedPet.getTags()){
            if (singleTag.getName().equalsIgnoreCase(data.get("tag"))){
                flag = true;
            }
        }
        assertTrue(flag);
    }

    @And("updates pet with name {string} and status {string}")
    public void updatesPetWithNameAndStatus(String newName, String newStatus) throws JsonProcessingException {
        reqSpec = given()
                .baseUri(config.getBaseURI())
                .queryParam("name",newName)
                .queryParam("status",newStatus)
                .contentType("application/json").log().all();
        response = reqSpec.post("/pet/"+pet.getId());

        ObjectMapper mapper4 = new ObjectMapper();
        formPet = mapper4.readValue(response.asString(),Pet.class);
        assertEquals(formPet.getName(),newName);
        assertEquals(formPet.getStatus(),newStatus);
    }

    @And("message {string} is returned")
    public void messageIsReturned(String deleteMsg) {
        assertEquals(response.asString(),deleteMsg);
    }

    @Then("user verify that DELETE API call is successful and status code {int} is returned")
    public void userVerifyThatDELETEAPICallIsSuccessfulAndStatusCodeIsReturned(int arg0) {
        assertEquals(response.getStatusCode(),arg0);
    }
}
