package apis;

import static constants.APIPaths.*;

import http.BaseAPI;
import io.restassured.response.Response;

public class GetBookingAPI extends BaseAPI {

    public GetBookingAPI() {
        super();
        super.logAllRequestData().logAllResponseData();
    }

    public Response getAllBookingIds() {
        super.setBasePath(GET_BOOKING_IDs.getApiPath());
        return super.sendRequest(GET_BOOKING_IDs.getMethodType());
    }

    public Response getBookingId(int bookingId) {
        super.setBasePath(GET_BOOKING.getApiPath());
        super.setPathParam("bookingId", bookingId);
        return super.sendRequest(GET_BOOKING.getMethodType());
    }

}
