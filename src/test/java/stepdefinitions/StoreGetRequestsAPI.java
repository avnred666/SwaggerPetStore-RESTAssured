package stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.StoreInventory;
import utilities.ConfigProperties;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class StoreGetRequestsAPI {
    RequestSpecification reqSpecification;
    Response response;
    ConfigProperties configProperties = new ConfigProperties();
    StoreInventory store;

    public StoreGetRequestsAPI() throws IOException {
    }

    @When("user submits GET request for inventory status")
    public void user_submits_get_request_for_inventory_status() throws JsonProcessingException {
        reqSpecification = given()
                .baseUri(configProperties.getBaseURI())
                .contentType("application/json").log().all();
        response = reqSpecification.get("/store/inventory");
        ObjectMapper mapper = new ObjectMapper();
        store = mapper.readValue(response.asString(),StoreInventory.class);

    }
    @Then("user verify that store GET API call is successful and status code {int} is returned")
    public void user_verify_that_store_get_api_call_is_successful_and_status_code_is_returned(Integer int1) {
        assertEquals(response.getStatusCode(),int1);
    }
    @Then("values are updated as follows:")
    public void values_are_updated_as_follows(DataTable dataTable) {
        Map<String,String> dataReturned = dataTable.asMap(String.class,String.class);
//        assertEquals(dataReturned.get("approved"),store.getApproved());
        assertEquals(dataReturned.get("placed"),store.getPlaced());
        assertEquals(dataReturned.get("delivered"),store.getDelivered());


    }
}
