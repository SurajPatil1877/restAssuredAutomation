package util;

import com.github.javafaker.Faker;
import lombok.Getter;
import pojo.dataHandler.BookingDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataHelper {
    @Getter
    private final static Faker FAKER = Faker.instance();


    public static String getFurtureDate(int plusDays, DateTimeFormatter dateTimeFormatter) {
        return LocalDate.now()
                        .plusDays(plusDays)
                        .format(dateTimeFormatter);
    }

//    public static BookingDetails getBookingDetails() {
//
//        return BookingDetails.builder().firstName(FAKER.name().firstName())
//                             .lastName(FAKER.name().lastName())
//                             .totalPrice(3000)
//                             .depositPaid(FAKER.bool().bool())
//                             .checkIn(getFurtureDate(20, DateTimeFormatter.ISO_DATE))
//                             .checkOut(getFurtureDate(2, DateTimeFormatter.ISO_DATE))
//                             .additionalNeeds(FAKER.food().dish())
//                             .build();
//    }

    public static int getRandomInt(int numberOfDigits) {
        return Math.toIntExact(FAKER.number().randomNumber(numberOfDigits, true));
    }

}
