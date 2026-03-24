package apis;

import http.BaseAPI;
import io.restassured.response.Response;

import static constants.APIPaths.*;

public class DeleteBookingAPI extends BaseAPI {

    public DeleteBookingAPI() {
        super();
        super.logAllRequestData().logAllResponseData();
    }

    public Response deleteBookingById(int id, String username, String password) {
        super.setBasePath(DELETE_BOOKING.getApiPath());
        super.setPathParam("bookingId", id);
        super.setBasicAuth(username, password);
        return super.sendRequest(DELETE_BOOKING.getMethodType());
    }
}
