package util;

import pojo.dataHandler.BookingDetails;
import pojo.request.BookingDatesRequest;
import pojo.request.CreateBookingRequest;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestHelper {

    public static CreateBookingRequest getCreateBookingRequestPojo(BookingDetails bookingDetails) {
        BookingDatesRequest bookingDates = BookingDatesRequest.builder().checkIn(bookingDetails.getCheckIn()).checkOut(bookingDetails.getCheckOut()).build();

        return CreateBookingRequest.builder()
                                   .firstName(bookingDetails.getFirstName())
                                   .lastName(bookingDetails.getLastName())
                                   .totalPrice(bookingDetails.getTotalPrice())
                                   .depositPaid(bookingDetails.isDepositPaid())
                                   .additionalNeeds(bookingDetails.getAdditionalNeeds())
                                   .bookingDates(bookingDates)
                                   .build();
    }

    public static Map<String, Object> getCreateBookingPayload(BookingDetails bookingDetails) {
        Map<String, Object> bookingDatesMap = new HashMap<>(2);
        bookingDatesMap.put("checkin", bookingDetails.getCheckIn());
        bookingDatesMap.put("checkout", bookingDetails.getCheckOut());

        Map<String, Object> details = new HashMap<>(6);
        details.put("firstname", bookingDetails.getFirstName());
        details.put("lastname", bookingDetails.getLastName());
        details.put("totalprice", bookingDetails.getTotalPrice());
        details.put("depositpaid", bookingDetails.isDepositPaid());
        details.put("additionalneeds", bookingDetails.getAdditionalNeeds());
        details.put("bookingdates", bookingDatesMap);

        return details;
    }

}
