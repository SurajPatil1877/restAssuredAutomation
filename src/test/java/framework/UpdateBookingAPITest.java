package framework;

import apis.CreateBookingAPI;
import apis.UpdateBookingAPI;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;

import java.util.Map;

import static util.ApiRequestHelper.getCreateBookingPayload;

public class UpdateBookingAPITest {

    private UpdateBookingAPI updateBookingAPI;

    @BeforeClass
    public void initAPI() {
        this.updateBookingAPI = new UpdateBookingAPI();
    }

    @Test(description = "")
    public void updateBooking() {
        //Creating new request
        Map<String, Object> createBookingPayload = getCreateBookingPayload(getBookingDetails());

        ValidatableResponse validatableResponse = new CreateBookingAPI()
                .createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200);
        //fetching booking id from response
        int bookingid = validatableResponse.extract().jsonPath().getInt("bookingid");

        //Updating the data
        createBookingPayload.replace("firstname", "Adhit");
        createBookingPayload.replace("totalprice", 5000);
        createBookingPayload.replace("depositpaid", true);

        //hitting the update request
        this.updateBookingAPI.updateBooking(createBookingPayload, bookingid, "admin", "password123");
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
