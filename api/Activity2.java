package activities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Activity2 {
    //POST request on https://petstore.swagger.io/v2/user
    @Test(priority=3)
    public void AddNewUser() throws IOException {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/user";
        // Write the request body
       //Import JSON file
        File file = new File("C:\\FSTProject\\FST_RestAssured\\src\\test\\inputActivity2.jason");
        FileInputStream inputJSON = new FileInputStream(file);
        // Get all bytes from JSON file
        byte[] bytes = new byte[(int) file.length()];
        inputJSON.read(bytes);
        // Read JSON file as String
        String reqBody = new String(bytes, "UTF-8");

        System.out.println(reqBody);

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
    //GET request on https://petstore.swagger.io/v2/user/{username}
    @Test(priority=2)
    public void GetUser() {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/user/{username}";

        //Set file path
        File resJSONFile = new File("C:\\FSTProject\\FST_RestAssured\\src\\test\\outputActivity2.jason");

        // Send GET Request
        Response response=
                given().contentType(ContentType.JSON)
                        .pathParam("username", "Sur")// Set headers
                        .when().get(ROOT_URI); // Get pet details with GET

        String body = response.getBody().asPrettyString();

        try {
            // Create log file
            resJSONFile.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(resJSONFile.getPath());
            writer.write(body);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
        System.out.println(body);
        response.then().body("id", equalTo(18));
        response.then().body("username", equalTo("Sur"));
        response.then().body("firstName", equalTo("Activity2"));
        response.then().body("lastName", equalTo("User"));
        response.then().body("email", equalTo("activity2user@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9912763450"));

    }

    //DELETE request on https://petstore.swagger.io/v2/user/{username}
    @Test(priority=3)
    public void DeleteUser() {
        // Set Base URL
        String ROOT_URI = "https://petstore.swagger.io/v2/user/{username}";
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
