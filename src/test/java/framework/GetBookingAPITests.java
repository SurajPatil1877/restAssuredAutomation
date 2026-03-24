package framework;

import apis.GetBookingAPI;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBookingAPITests {

    private GetBookingAPI getBookingAPI;

    @BeforeClass
    public void initAPI() {
        this.getBookingAPI = new GetBookingAPI();
    }

    @Test(description = "Basic HTTP Status check for get booking ids API")
    public void validateStatusCodeForGetBookingIdAPI() {
        getBookingAPI.getAllBookingIds()
                     .then().assertThat().statusCode(200);
    }

    @Test(description = "Basic HTTP Status check for get booking id API")
    public void validateStatusCodeForGetBookingByIdAPI() {
        getBookingAPI.getBookingId(20)
                     .then().assertThat().statusCode(200);
    }
}
