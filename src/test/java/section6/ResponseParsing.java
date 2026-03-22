package section6;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.request.BookingDatesRequest;
import pojo.request.CreateBookingRequest;
import pojo.response.BookingResponse;
import pojo.response.CreateBookingResponse;

import static org.hamcrest.Matchers.*;

public class ResponseParsing {
    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final RequestSpecification request = RestAssured.given()
                                                            .and().baseUri(this.baseURI)
                                                            .and().filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    /**
     * We are using RestAssured's {@link io.restassured.response.ResponseBodyExtractionOptions#as(Class)}
     * to parse response as Java class
     */
    @Test
    public void parseResponseIntoPojo() {
        CreateBookingRequest requestBody = this.getCreateBookingRequestPojo("Suraj", "Patil", 600);

        Response createBookingAPIResponse = this.request.basePath("/booking")
                                                        .and().contentType(ContentType.JSON)
                                                        .and().body(requestBody)
                                                        .when().post()
                                                        .then().assertThat().statusCode(200)
                                                        .and().body("bookingid", is(not(equalTo(0))))
                                                        .extract().response();

        //Parse response as a class
        CreateBookingResponse createBookingResponse = createBookingAPIResponse.as(CreateBookingResponse.class);
        BookingResponse booking = createBookingResponse.getBooking();
        Assert.assertEquals(booking.getFirstName(), "Suraj", "firstName is not correct");
        Assert.assertEquals(booking.getBookingDates().getCheckIn(), "2024-01-01", "Checkin date is not correct");
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

}
