package framework;

import apis.CreateBookingAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;

import static org.hamcrest.Matchers.*;
import static util.ApiRequestHelper.getCreateBookingPayload;
import static util.ApiRequestHelper.getCreateBookingRequestPojo;

public class CreateBookingAPITest {

    private CreateBookingAPI createBookingAPI;

    @BeforeClass
    public void initAPI() {
        this.createBookingAPI = new CreateBookingAPI();
    }

    @Test(description = "Create a new booking and validate HTTP Status Code With Payload")
    public void createAndValidateStatusCodeWithPayload() {
        BookingDetails bookingDetails = getBookingDetails();
        this.createBookingAPI
                .createNewBooking(getCreateBookingPayload(bookingDetails))
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));
    }

    @Test(description = "Create a new booking and validate HTTP Status Code")
    public void createAndValidateStatusCodeWithPojo() {
        this.createBookingAPI
                .createNewBooking(getCreateBookingRequestPojo(getBookingDetails()))
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));
    }

    private static BookingDetails getBookingDetails() {
        return BookingDetails.builder().firstName("Suraj")
                             .lastName("Patil")
                             .totalPrice(3000)
                             .depositPaid(false)
                             .checkIn("2026-02-02")
                             .checkOut("2025-05-06")
                             .build();
    }
}
