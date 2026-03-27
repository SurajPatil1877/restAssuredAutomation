package framework;

import apis.GetBookingAPI;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GetBookingAPITests {

    @Parameters("testParam")
    @Test(description = "Basic HTTP Status check for get booking ids API")
    public void validateStatusCodeForGetBookingIdAPI(@Optional String testParam) {
        System.out.println("Test Param: " + testParam);
        new GetBookingAPI().getAllBookingIds()
                           .then().assertThat().statusCode(200);
    }

    @Test(description = "Basic HTTP Status check for get booking id API")
    public void validateStatusCodeForGetBookingByIdAPI() {
        new GetBookingAPI().getBookingId(20)
                           .then().assertThat().statusCode(200);
    }
}
