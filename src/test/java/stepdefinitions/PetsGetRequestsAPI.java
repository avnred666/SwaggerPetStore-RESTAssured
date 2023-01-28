package stepdefinitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet;
import pojo.Tag;
import utilities.ConfigProperties;
import java.io.IOException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PetsGetRequestsAPI {
    ConfigProperties config;
    Response response;
    RequestSpecification reqSpec;
    Pet[] pets;
    Pet singlePet;

    @When("user performs pets {string} using {string} parameter")
    public void user_performs_pets_using_request(String resource, String value) throws IOException {
        config = new ConfigProperties();
        if (resource.equalsIgnoreCase("findByStatus")){
            reqSpec = RestAssured.given()
                    .baseUri(config.getBaseURI())
                    .queryParam("status",value)
                    .contentType("application/json").log().all();

            response = reqSpec.get("/pet/findByStatus");
        }
        else if(resource.equalsIgnoreCase("findByTags")){
            reqSpec = RestAssured.given()
                    .baseUri(config.getBaseURI())
                    .queryParam("tags",value)
                    .contentType("application/json").log().all();
            response = reqSpec.get("/pet/findByTags");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        pets = objectMapper.readValue(response.asString(), Pet[].class);
    }
    @Then("user verify that API call is successful and status code {int} is returned")
    public void user_verify_that_api_call_is_successful_and_status_code_is_returned(Integer statusCode) {
        assertEquals(response.getStatusCode(),statusCode);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        boolean flag = false;
        for (Pet pet: pets){
            if(keyValue.equalsIgnoreCase("status")){
                assertEquals(pet.getStatus(),expectedValue);
            }
            else if(keyValue.equalsIgnoreCase("tags")){
                for(Tag tag: pet.getTags()){
                    if(tag.getName().equalsIgnoreCase(expectedValue)){
                        flag = true;
                    }
                    assertTrue(flag);
                }
            }
        }
    }

    @When("user finds pets using {string} using {string} parameter")
    public void user_finds_pets_using_using_parameter(String resource, String value) throws IOException {
        config = new ConfigProperties();
        if(resource.equalsIgnoreCase("findByID")){
            reqSpec = RestAssured.given()
                    .baseUri(config.getBaseURI())
                    .contentType("application/json").log().all();

            response = reqSpec.get("/pet/"+value);
        }
        ObjectMapper mapper = new ObjectMapper();
        singlePet = mapper.readValue(response.asString(), Pet.class);


    }
    @Then("{string} in response is {int}")
    public void in_response_is(String keyValue, int expectedValue) {
        assertEquals(singlePet.getId(),expectedValue);
    }
}
