package stepdefinitions;

import testdata.BuildOrderData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Order;
import utilities.ConfigProperties;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class StorePostRequestsAPI {
    Order orderData,responseOrderData;
    BuildOrderData buildOrderData = new BuildOrderData();
    RequestSpecification reqSpec;
    ConfigProperties config = new ConfigProperties();
    Response response;
    String orderBody;

    public StorePostRequestsAPI() throws IOException {
    }


    @Given("add order payload")
    public void add_order_payload() throws JsonProcessingException {
        orderData = buildOrderData.buildOrderDataJSON();
        ObjectMapper mapper = new ObjectMapper();
        orderBody = mapper.writeValueAsString(orderData);

    }
    @When("user places an order for a pet")
    public void user_places_an_order_for_a_pet() {
        reqSpec = given()
                .baseUri(config.getBaseURI())
                .contentType("application/json")
                .body(orderBody).log().all();
        response = reqSpec.post("store/order");

    }

    @Then("user verify that following values are returned")
    public void user_verify_that_following_values_are_returned(DataTable dataTable) throws JsonProcessingException {
        ObjectMapper mapper1 = new ObjectMapper();
        responseOrderData = mapper1.readValue(response.asString(),Order.class);
        Map<String,String> map = dataTable.asMap(String.class,String.class);
        assertEquals(responseOrderData.getStatus(),map.get("status"));
        assertEquals(responseOrderData.isComplete(),Boolean.parseBoolean(map.get("complete")));
    }

    @Then("user verify that status code {int} is returned")
    public void userVerifyThatStatusCodeIsReturned(int arg0) {
        assertEquals(response.getStatusCode(),arg0);
    }
}
