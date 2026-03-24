package apis;

import http.BaseAPI;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

import static constants.APIPaths.UPDATE_BOOKING;

public class UpdateBookingAPI extends BaseAPI {

    public UpdateBookingAPI() {
        super();
        super.logAllSpecificRequestDetail(LogDetail.BODY).logAllResponseData();
        super.setContentType(ContentType.JSON);
    }

    public Response updateBooking(Map<String, Object> createBookingPayload, int bookingId, String username, String password) {
        return getCreateBookingAPIResponse(createBookingPayload, bookingId, username, password);
    }

    public Response updateBooking(CreateBookingRequest createBookingRequest, int bookingId, String username, String password) {
        return getCreateBookingAPIResponse(createBookingRequest, bookingId, username, password);
    }

    private Response getCreateBookingAPIResponse(Object createBookingPayload, int bookingId, String username, String password) {
        super.setBasePath(UPDATE_BOOKING.getApiPath());
        super.setPathParam("bookingId", bookingId);
        super.setRequestBody(createBookingPayload);
        super.setBasicAuth(username, password);
        return super.sendRequest(UPDATE_BOOKING.getMethodType());
    }


}
