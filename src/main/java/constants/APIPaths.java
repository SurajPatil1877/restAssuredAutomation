package constants;


import static io.restassured.http.Method.*;


import io.restassured.http.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum APIPaths {

    GET_BOOKING("/booking/{bookingId}", GET),
    GET_BOOKING_IDs("/booking", GET),
    CREATE_BOOKING("/booking", POST),
    DELETE_BOOKING("/booking/{bookingId}", DELETE),
    UPDATE_BOOKING("/booking/{bookingId}", PUT);

    private final String apiPath;
    private final Method methodType;
}
