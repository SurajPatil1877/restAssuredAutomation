package framework;

import apis.CreateBookingAPI;
import apis.DeleteBookingAPI;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.IntStream;

import static util.ApiRequestHelper.getCreateBookingPayload;


public class DeleteBookingAPITest {


    private DeleteBookingAPI deleteBookingAPI;
    private Faker FAKER;

    @BeforeClass
    public void initAPI() {
        this.deleteBookingAPI = new DeleteBookingAPI();
        this.FAKER = this.deleteBookingAPI.FAKER;
    }

    @Test(description = "Delete and existing booking", dataProvider = "bookingDataWithStreamForDelete")
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

    @DataProvider(name = "bookingDataWithStreamForDelete")
    public Object[][] bookingDataWithStream() {
        DateTimeFormatter isoDate = deleteBookingAPI.ISO_DATE;
        Name name = FAKER.name();
        return IntStream.range(0, 3)
                        .mapToObj(i -> {
                            int checkInDate = TestDataHelper.getRandomInt(2);
                            return new Object[]{
                                    name.firstName(), name.lastName(),
                                    FAKER.bool().bool(), FAKER.food().dish(),
                                    FAKER.number().randomNumber(3, true),
                                    TestDataHelper.getFurtureDate(checkInDate, isoDate),
                                    TestDataHelper.getFurtureDate(checkInDate + 4, isoDate)};
                        }).toArray(Object[][]::new);
    }

}
