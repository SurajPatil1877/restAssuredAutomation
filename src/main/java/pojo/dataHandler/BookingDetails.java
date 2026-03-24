package pojo.dataHandler;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookingDetails {
    private String firstName;
    private String lastName;
    private String checkIn;
    private String checkOut;
    private boolean depositPaid;
    private int totalPrice;
    private String additionalNeeds;
}
