package section6;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import pojo.request.BookingDatesRequest;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class SimplePostAPI {

    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final RequestSpecification request = RestAssured.given()
                                                            .and().baseUri(this.baseURI)
                                                            .and().filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    //Using Map as request Body
    @Test
    public void simplePostAPIToCreateBooking() {
        Map<String, Object> requestBody = getCreateBookingPayload("Suraj", "Patil", 500);


        Response createBookingAPIResponse = this.request.basePath("/booking")
                                                        //Always use ContentType ENUM
                                                        .and().contentType(ContentType.JSON)
                                                        .and().body(requestBody)
                                                        .when().post()
                                                        .then().assertThat().statusCode(200)
                                                        .and().body("bookingid", is(not(equalTo(0))))
                                                        .extract().response();
    }

    @Test
    public void simplePutAPIToUpdateBooking() {
        Map<String, Object> requestBody = getCreateBookingPayload("Suraj", "Patil", 1000);
        int bookingId = 1546;

        this.request.basePath("/booking/{bookingId}")
                    //Always use ContentType ENUM
                    .and().contentType(ContentType.JSON)
                    .and().body(requestBody)
                    .and().pathParam("bookingId", bookingId)
                    .and().auth().preemptive().basic("admin", "password123")
                    .when().put()
                    .then().assertThat().statusCode(200)
                    .extract().response();
    }


    //this test use simple pojo class for creating a booking
    @Test
    public void simplePostAPIWithPojoToCreateBooking() {
        CreateBookingRequest requestBody = this.getCreateBookingRequestPojo("Suraj", "Patil", 600);

        this.request.basePath("/booking")
                    .and().contentType(ContentType.JSON)
                    .and().body(requestBody)
                    .when().post()
                    .then().assertThat().statusCode(200)
                    .and().body("bookingid", is(not(equalTo(0))))
                    .extract().response();
    }



    private CreateBookingRequest getCreateBookingRequestPojo(String firstName, String lastName, int totalPrice) {
        BookingDatesRequest bookingDates = BookingDatesRequest.builder().checkIn("2024-01-01").checkOut("2024-02-01").build();

        return CreateBookingRequest.builder()
                                   .firstName(firstName)
                                   .lastName(lastName)
                                   .totalPrice(totalPrice)
                                   .depositPaid(false)
                                   .additionalNeeds("Nothing else")
                                   .bookingDates(bookingDates)
                                   .build();
    }

    private Map<String, Object> getCreateBookingPayload(String firstName, String lastName, int totalPrice) {
        Map<String, Object> bookingDatesMap = Map.of(
                "checkin", "2024-01-01",
                "checkout", "2024-02-01");

        return Map.of(
                "firstname", firstName,
                "lastname", lastName,
                "totalprice", totalPrice,
                "depositpaid", false,
                "additionalneeds", "Nothing else",
                "bookingdates", bookingDatesMap);
    }
}
