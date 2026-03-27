package framework;

import apis.GetBookingAPI;
import org.awaitility.Awaitility;
import org.testng.annotations.Test;

import java.time.Duration;

public class AwaitilityTest {

    @Test
    public void waitUnitAsserted() {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        Awaitility.await()
                  .and().with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .then().untilAsserted(() ->
                          getBookingAPI.getBookingId(1)
                                       .then().assertThat().statusCode(200));
    }

    @Test
    public void waitUnit() {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        Awaitility.await()
                  .and().with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .then().until(() -> {
                      int statusCode = getBookingAPI.getBookingId(20).statusCode();
                      return statusCode == 400;
                  });
    }


    @Test
    public void waitUnitAndIgnoreAllExceptions() {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        Awaitility.await()
                  .and().with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .and().ignoreExceptions()
                  .then().until(() -> {
                      getBookingAPI.getBookingId(20)
                                   .then().assertThat().statusCode(400);
                      return true;
                  });
    }


    @Test
    public void waitUnitAndIgnoreSpecificException() {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        Awaitility.await()
                  .and().with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .and().ignoreExceptionsInstanceOf(AssertionError.class)
                  .then().until(() -> {
                      getBookingAPI.getBookingId(20)
                                   .then().assertThat().statusCode(400);
                      return true;
                  });
    }


    @Test
    public void definePollingDelay() {
        GetBookingAPI getBookingAPI = new GetBookingAPI();
        Awaitility.await()
                  .and().with().alias("My custom message")
                  .and().with().timeout(Duration.ofSeconds(5))
                  .and().ignoreExceptionsInstanceOf(AssertionError.class)
                  .and().pollDelay(Duration.ofMillis(1000))
                  .then().until(() -> {
                      getBookingAPI.getBookingId(20)
                                   .then().assertThat().statusCode(400);
                      return true;
                  });
    }
}
