package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // Headers
    Map<String, String> headers = new HashMap<>();
    // API Resource Path
    String resourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        // Headers
        headers.put("Content-Type", "application/json");

        // Request and Response body
        DslPart requestResponseBody = new PactDslJsonBody().
             numberType("id", 912).
             stringType("firstName", "Arya").
             stringType("lastName", "Stark").
             stringType("email", "arya.stark@got.com");

        return builder.given("A request to create a user").
                uponReceiving("A request to create a user").
                    method("POST").
                    path(resourcePath).
                    headers(headers).
                    body(requestResponseBody).
                willRespondWith().
                    status(201).
                body(requestResponseBody).
                toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider" , port = "8282")
    public void consumerTest(){
        // BaseURI
        String baseUri = "http://localhost:8282";

        // Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 912);
        reqBody.put("firstName", "Arya");
        reqBody.put("lastName", "Stark");
        reqBody.put("email", "arya.stark@got.com");

        //Generate Response
        given().headers(headers).body(reqBody).log().all().
        when().post(baseUri + resourcePath).
        then().statusCode(201).log().all();
    }


}
