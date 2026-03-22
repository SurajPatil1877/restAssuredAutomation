package pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateBookingResponse {

	@JsonProperty("booking")
	private BookingResponse booking;

	@JsonProperty("bookingid")
	private int bookingId;

}