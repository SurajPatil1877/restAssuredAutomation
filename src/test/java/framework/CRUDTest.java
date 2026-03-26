package framework;

import apis.CreateBookingAPI;
import apis.DeleteBookingAPI;
import apis.GetBookingAPI;
import apis.UpdateBookingAPI;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static util.ApiRequestHelper.getCreateBookingPayload;

public class CRUDTest extends BaseTest {


    @Test(description = "CRUD Operation on Restfull Booker API resources", dataProvider = "bookingDataWithStream")
    public void crudTest(String firstName, String lastName, boolean isDepositPaid,
                         String additionalNeeds, int totalPrice, String checkInDate, String checkOutDate) {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        //Creating new request
        BookingDetails bookingDetails = BookingDetails.builder().firstName(firstName)
                                                      .lastName(lastName)
                                                      .totalPrice(Math.toIntExact(totalPrice))
                                                      .depositPaid(isDepositPaid)
                                                      .checkIn(checkInDate)
                                                      .checkOut(checkOutDate)
                                                      .additionalNeeds(additionalNeeds)
                                                      .build();

        Map<String, Object> createBookingPayload = getCreateBookingPayload(bookingDetails);

        ValidatableResponse validatableResponse = new CreateBookingAPI()
                .createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200);
        //fetching booking id from response
        int bookingid = validatableResponse.extract().jsonPath().getInt("bookingid");

        Response getBookingByIdApiResponse = getBookingAPI.getBookingId(bookingid);
        validateRetrieveBookinDetails(firstName, lastName, isDepositPaid, totalPrice, checkInDate, checkOutDate, getBookingByIdApiResponse);

        //Updating the data
        String updatedFirstName = FAKER.name().firstName();
        int updatedTotalPrice = Math.toIntExact(FAKER.number().randomNumber(3, true));
        boolean updatedDepositPaid = FAKER.bool().bool();

        createBookingPayload.replace("firstname", updatedFirstName);
        createBookingPayload.replace("totalprice", updatedTotalPrice);
        createBookingPayload.replace("depositpaid", updatedDepositPaid);

        //hitting the update request
        Response updateDataRepsonse = new UpdateBookingAPI().updateBooking(createBookingPayload, bookingid, "admin", "password123");

        validateRetrieveBookinDetails(updatedFirstName, lastName, updatedDepositPaid, updatedTotalPrice, checkInDate, checkOutDate, updateDataRepsonse);

        //DeleteAPI
        new DeleteBookingAPI().deleteBookingById(bookingid, "admin", "password123")
                              .then().assertThat().statusCode(201);

        getBookingAPI.getBookingId(bookingid)
                     .then().assertThat().statusCode(404);
    }

    private static void validateRetrieveBookinDetails(String firstName, String lastName, boolean isDepositPaid, int totalPrice, String checkInDate, String checkOutDate, Response response) {
        response
                .then().assertThat().statusCode(200)
                .and().body("firstname", is(equalTo(firstName)))
                .and().body("lastname", is(equalTo(lastName)))
                .and().body("totalprice", is(equalTo(totalPrice)))
                .and().body("depositpaid", is(equalTo(isDepositPaid)))
                .and().rootPath("bookingdates")
                .and().body("checkin", is(equalTo(checkInDate)))
                .and().body("checkout", is(equalTo(checkOutDate)))
                .and().detachRootPath("bookingdates");
    }

}
