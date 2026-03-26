package framework;

import apis.CreateBookingAPI;
import apis.DeleteBookingAPI;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;

import java.util.Map;

import static util.ApiRequestHelper.getCreateBookingPayload;


public class DeleteBookingAPITest extends BaseTest {


    private DeleteBookingAPI deleteBookingAPI;


    @BeforeClass
    public void initAPI() {
        this.deleteBookingAPI = new DeleteBookingAPI();
    }

    @Test(description = "Delete and existing booking", dataProvider = "bookingDataWithStream")
    public void deleteBookingTest(String firstName, String lastName, boolean isDepositPaid,
                                  String additionalNeeds, long totalPrice, String checkInDate, String checkOutDate) {

        BookingDetails bookingDetails = BookingDetails.builder().firstName(firstName)
                                                      .lastName(lastName)
                                                      .totalPrice(Math.toIntExact(totalPrice))
                                                      .depositPaid(isDepositPaid)
                                                      .checkIn(checkInDate)
                                                      .checkOut(checkOutDate)
                                                      .additionalNeeds(additionalNeeds)
                                                      .build();

        //Creating new request
        Map<String, Object> createBookingPayload = getCreateBookingPayload(bookingDetails);

        ValidatableResponse validatableResponse = new CreateBookingAPI()
                .createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200);
        //fetching booking id from response
        int bookingid = validatableResponse.extract().jsonPath().getInt("bookingid");
        this.deleteBookingAPI.deleteBookingById(bookingid, "admin", "password123")
                             .then().assertThat().statusCode(201);

    }
}
