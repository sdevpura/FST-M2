package activities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Activity1 {
    //POST request on https://petstore.swagger.io/v2/pet
    @Test(priority=3)
    public void AddNewPet() {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/pet";
        // Write the request body
        String reqBody = "{"
                + "\"id\": 290822,"
                + "\"name\": \"Shifu\","
                + " \"status\": \"alive\""
                + "}";

    Response response =
            given().contentType(ContentType.JSON) // Set headers
                    .body(reqBody).when().post(ROOT_URI); // Send POST request

    // Print response of POST request
    String body = response.getBody().asPrettyString();
    System.out.println(body);

    //Assertions
        response.then().body("id", equalTo(290822));
        response.then().body("name", equalTo("Shifu"));
        response.then().body("status", equalTo("alive"));



}
    //GET request on https://petstore.swagger.io/v2/pet/{petId}
    @Test(priority=2)
    public void GetNewPet() {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/pet/{petId}";

        // Send GET Request
        Response response=
                given().contentType(ContentType.JSON)
                        .pathParam("petId", 290822)// Set headers
                .when().get(ROOT_URI); // Get pet details with GET

        String body = response.getBody().asPrettyString();
        System.out.println(body);
        response.then().body("id", equalTo(290822));
        response.then().body("name", equalTo("Shifu"));
        response.then().body("status", equalTo("alive"));

    }

    //DELETE request on https://petstore.swagger.io/v2/pet/{petId}
    @Test(priority=3)
    public void DeletePet() {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/pet/{petId}";
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().pathParam("petId", "290822") // Set path parameter
                        .delete(ROOT_URI); // Send DELETE request

        String body = response.getBody().asPrettyString();
        System.out.println(body);

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("290822"));

        }

}
