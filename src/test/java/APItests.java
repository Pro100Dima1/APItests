import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APItests {
    private  final int petId = 1234444;

    @BeforeEach
    public void setUp(){
        baseURI = "https://petstore.swagger.io/v2/";
    }

    @Test
    public void petNotFoundTest(){
        given().when()
                .get(baseURI + "pet/{id}", petId)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("message", equalTo("Pet not found"));
    }
}
