package framework;

import apis.CreateBookingAPI;
import apis.UpdateBookingAPI;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.ApiRequestHelper.getCreateBookingPayload;

public class UpdateBookingAPITest {

    private UpdateBookingAPI updateBookingAPI;
    private Faker FAKER;


    @BeforeClass
    public void initAPI() {
        this.updateBookingAPI = new UpdateBookingAPI();
        this.FAKER = this.updateBookingAPI.FAKER;
    }

    @Test(description = "Update the newly created booking and validate the status code", dataProvider = "bookingDataWithForLoop")
    public void updateAndValidateTheBookingStatusCode(String firstName, String lastName, boolean isDepositPaid,
                                                      String additionalNeeds, long totalPrice, String checkInDate, String checkOutDate) {
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

        //Updating the data
        createBookingPayload.replace("firstname", FAKER.name().firstName());
        createBookingPayload.replace("totalprice", Math.toIntExact(FAKER.number().randomNumber(3, true)));
        createBookingPayload.replace("depositpaid", FAKER.bool().bool());

        //hitting the update request
        this.updateBookingAPI.updateBooking(createBookingPayload, bookingid, "admin", "password123");
    }

    @DataProvider(name = "bookingDataWithForLoop")
    public Object[][] bookingDataWithLoop() {
        DateTimeFormatter isoDate = updateBookingAPI.ISO_DATE;
        Name name = FAKER.name();
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int checkInDate = TestDataHelper.getRandomInt(2);
            Object[] objects = new Object[]{
                    name.firstName(),
                    name.lastName(),
                    FAKER.bool().bool(),
                    FAKER.food().dish(),
                    FAKER.number().randomNumber(3, true),
                    TestDataHelper.getFurtureDate(checkInDate, isoDate),
                    TestDataHelper.getFurtureDate(checkInDate + 4, isoDate)
            };
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }
}
