package framework;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.testng.annotations.DataProvider;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class BaseTest {
    protected final Faker FAKER = TestDataHelper.getFAKER();

    @DataProvider(name = "bookingDataWithStream")
    public Object[][] bookingDataWithStream() {
        DateTimeFormatter isoDate = DateTimeFormatter.ISO_DATE;
        Name name = FAKER.name();
        return IntStream.range(0, 3)
                        .mapToObj(i -> {
                            int checkInDate = TestDataHelper.getRandomInt(2);
                            return new Object[]{
                                    name.firstName(), name.lastName(),
                                    FAKER.bool().bool(), FAKER.food().dish(),
                                    Math.toIntExact(FAKER.number().randomNumber(3, true)),
                                    TestDataHelper.getFurtureDate(checkInDate, isoDate),
                                    TestDataHelper.getFurtureDate(checkInDate + 4, isoDate)};
                        }).toArray(Object[][]::new);
    }
}
