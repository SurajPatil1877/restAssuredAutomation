package section4;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class ResponseExtraction {
    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final RequestSpecification request = RestAssured.given()
                                                            .and().baseUri(this.baseURI)
                                                            .and().filters(new RequestLoggingFilter(), new RequestLoggingFilter());

    @Test
    public void extractJsonPathFieldFromResponse() {
        String basePath = "/booking/{bookingId}";
        Response inLineValidatableResponse = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat()
                .body("totalprice", is(equalTo(111)))
                .and().body("bookingdates.checkin", is(notNullValue()))
                .extract().response();

        int totalPrice = inLineValidatableResponse.jsonPath().getInt("totalprice");
        String checkingDate = inLineValidatableResponse.jsonPath().getString("bookingdates.checkin");
        Map<String, Object> bookingDatesMap = inLineValidatableResponse.jsonPath().getMap("bookingdates");

        System.out.printf("Checking Date is %s\n", checkingDate);
        System.out.printf("Total Price is %s\n", totalPrice);
        System.out.printf("Booking Date map is %s\n", bookingDatesMap);

//        inLineValidatableResponse.prettyPrint();
    }

    @Test
    public void jsonSchemaValidations() {
        String basePath = "/booking/{bookingId}";
        Response inLineValidatableResponse = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat()
                .body("totalprice", is(equalTo(111)))
                .and().body("bookingdates.checkin", is(notNullValue()))
                //validate json response schema
                .and().body(JsonSchemaValidator
                        .matchesJsonSchema(new File("src/test/resources/schema/getBookingByIdSchema.json")))
                .extract().response();

        inLineValidatableResponse.prettyPrint();

    }

    @Test
    public void setRootPath() {
        String basePath = "/booking/{bookingId}";
        Response inLineValidatableResponse = this.request
                .and().basePath(basePath)
                .and().pathParam("bookingId", 20)
                .when().get()
                .then().assertThat()
                //set the root path so that we can validate child booking dates
                // without using `bookingdates` json path again and again
                .rootPath("bookingdates")
                .and().body("checkin", is(notNullValue()))
                .and().body("checkout", is(notNullValue()))
                //detach the root path so that we can do validations outside the `bookingdates` rootpath
                .and().detachRootPath("bookingdates")
                // totalprice is outside `bookingdates`
                .and().body("totalprice", is(equalTo(111)))
                .extract().response();

        inLineValidatableResponse.prettyPrint();

    }
}
