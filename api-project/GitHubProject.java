import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubProject {
    RequestSpecification requestSpec;
    String publicSSHKey;
    int idSSHKey;


    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
    }


        //POST request to add a SSH key to your account
        @Test(priority=1)
        public void addSSHKey(){
            String reqBody = "{"
                    + "\"title\": \"TestAPIKey\","
                    + " \"key\": \"ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIN/QVtlJVJ135Uv8H+7FxqcYzzZuc2eHjPdpuA0qmZlh\""
                    + "}";
            Response response =
                    given().spec(requestSpec)
                            .body(reqBody)
                            .when().post("/user/keys");
           String body = response.getBody().asPrettyString();
            System.out.println(body);

            idSSHKey = response.then().extract().path("id");
            System.out.println(idSSHKey);
            response.then().statusCode(201);
        }

        // GET request to get all the SSH keys attached to your account
        @Test(priority=2)
        public void getSSHKey() {

        Response response =
        given().spec(requestSpec)
                .pathParam("keyId", idSSHKey)
                .when().get("/user/keys/{keyId}");


        String body = response.getBody().asPrettyString();
        System.out.println(body);
        Reporter.log(body);
        response.then().statusCode(200);

    }
        //DELETE request to delete the key attached to your account.
    @Test(priority=3)
    public void deleteSSHKey(){
    Response response =
            given().spec(requestSpec)
                    .pathParam("keyId", idSSHKey)
                    .when().delete("/user/keys/{keyId}");
    String body = response.getBody().asPrettyString();
    System.out.println(body);
    Reporter.log(body);
    response.then().statusCode(204);

    }



    }
