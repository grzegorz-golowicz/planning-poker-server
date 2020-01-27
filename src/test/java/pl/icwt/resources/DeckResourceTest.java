package pl.icwt.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@QuarkusTest
class DeckResourceTest {

    @Test
    void getDeck() {
        given()
                .when().get("/deck")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", hasItems("VAL_0", "VAL_05", "VAL_1", "VAL_2", "VAL_3", "VAL_5", "VAL_8", "VAL_13", "VAL_20", "VAL_40", "VAL_100", "INFINITY", "QUESTION_MARK"),
                        "label", hasItems("0", "0.5", "1", "2", "3", "5", "8", "13", "20", "40", "100", "INF", "?"));
    }

}