import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APItests {
    private  final int petId = 11;

    @BeforeEach
    public void setUp(){
        baseURI = "https://petstore.swagger.io/v2/";
    }

    @Test
    public void petNotFoundTest(){
        given().when()
                .get(baseURI + "pet/{id}", petId)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("message", equalTo("Pet not found"));
    }

    @Test
    public void petNotFoundTypeErrorTest(){
        given().when()
                .get(baseURI + "pet/{id}", petId)
                .then()
                .log().all()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type", equalTo("error"));
    }

    @Test
    public void petCreateTest(){
        Integer id = 11;
        String name = "Barsik";
        String status = "Lobster";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "pet/")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo(id));
    }

    @DisplayName("Проверка удаления созданного питомца")
    @Test
    public void createAndDeleteTest(){
        Integer id = 11;
        String name = "Barsik";
        String status = "Lobster";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "pet/")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        deletePet();
        petNotFoundTest();
    }

    public void deletePet(){
        given()
                .when()
                .delete(baseURI + "pet/{id}", petId)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
