package section4;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class Assertions {
    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final RequestSpecification request = RestAssured.given()
                                                            .and().baseUri(this.baseURI)
                                                            .and().filters(new RequestLoggingFilter(), new RequestLoggingFilter());

    @Test
    public void assertHTTPStatusCode() {
        String basePath = "/booking/{bookingId}";
        ValidatableResponse response = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat().statusCode(200);
    }

    @Test
    public void useJsonPathFieldValidation() {
        String basePath = "/booking/{bookingId}";
        ValidatableResponse validatableResponse = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat()
                .body("totalprice", is(equalTo(111)))
                .and().body("bookingdates.checkin", is(equalTo("2018-01-01")));
    }

    @Test
    public void nonNullFieldValidation() {
        String basePath = "/booking/{bookingId}";
        Response inLineValidatableResponse = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat()
                .body("totalprice", is(equalTo(111)))
                .and().body("bookingdates.checkin", is(notNullValue()))
                .extract().response();

        inLineValidatableResponse.prettyPrint();
    }
}
