package framework;

import apis.CreateBookingAPI;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.dataHandler.BookingDetails;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static util.ApiRequestHelper.getCreateBookingPayload;
import static util.ApiRequestHelper.getCreateBookingRequestPojo;

public class CreateBookingAPITest {

    private CreateBookingAPI createBookingAPI;
    private Faker FAKER;

    @BeforeClass
    public void initAPI() {
        this.createBookingAPI = new CreateBookingAPI();
        this.FAKER = this.createBookingAPI.FAKER;
    }

    @Test(description = "Create a new booking and validate HTTP Status Code With Payload", dataProvider = "bookingDataWithStreamForCreate")
    public void createAndValidateStatusCodeWithPayload(String firstName, String lastName, boolean isDepositPaid,
                                                       String additionalNeeds, long totalPrice, String checkInDate, String checkOutDate) {
        BookingDetails bookingDetails = BookingDetails.builder().firstName(firstName)
                                                      .lastName(lastName)
                                                      .totalPrice(Math.toIntExact(totalPrice))
                                                      .depositPaid(isDepositPaid)
                                                      .checkIn(checkInDate)
                                                      .checkOut(checkOutDate)
                                                      .additionalNeeds(additionalNeeds)
                                                      .build();
        this.createBookingAPI
                .createNewBooking(getCreateBookingPayload(bookingDetails))
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));
    }

    @Test(description = "Create a new booking and validate HTTP Status Code", dataProvider = "bookingDataWithStreamForCreate")
    public void createAndValidateStatusCodeWithPojo(String firstName, String lastName, boolean isDepositPaid,
                                                    String additionalNeeds, long totalPrice, String checkInDate, String checkOutDate) {
        BookingDetails bookingDetails = BookingDetails.builder().firstName(firstName)
                                                      .lastName(lastName)
                                                      .totalPrice(Math.toIntExact(totalPrice))
                                                      .depositPaid(isDepositPaid)
                                                      .checkIn(checkInDate)
                                                      .checkOut(checkOutDate)
                                                      .additionalNeeds(additionalNeeds)
                                                      .build();
        this.createBookingAPI
                .createNewBooking(getCreateBookingRequestPojo(bookingDetails))
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));
    }

    @DataProvider(name = "bookingDataWithStreamForCreate")
    public Object[][] bookingDataWithStream() {
        DateTimeFormatter isoDate = createBookingAPI.ISO_DATE;
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
