package apis;

import http.BaseAPI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static constants.APIPaths.CREATE_BOOKING;

public class CreateBookingAPI extends BaseAPI {

    public CreateBookingAPI() {
        super();
        super.logAllRequestData().logAllResponseData();
        super.setContentType(ContentType.JSON);
    }

    public Response createNewBooking(Map<String, Object> createBookingPayload) {
        return getCreateBookingAPIResponse(createBookingPayload);
    }

    public Response createNewBooking(CreateBookingRequest createBookingRequest) {
        return getCreateBookingAPIResponse(createBookingRequest);
    }

    private Response getCreateBookingAPIResponse(Object createBookingPayload) {
        super.setBasePath(CREATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        return super.sendRequest(CREATE_BOOKING.getMethodType());
    }
}
