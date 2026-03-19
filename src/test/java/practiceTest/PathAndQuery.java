package practiceTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.groovy.util.Maps;
import org.testng.annotations.Test;

import java.util.Map;

public class PathAndQuery {
    RequestSpecification request = RestAssured.given();
    String baseURI = "https://restful-booker.herokuapp.com";

    @Test
    public void getAPIWithURLParam() {
        String basePath = "/booking/{bookingId}";
        Response response = request.baseUri(baseURI)
                                   .basePath(basePath)
                                   .pathParam("bookingId", 2)
                                   .get();
        System.out.println(response.asString());
    }

    @Test
    public void getAPIWithQueryParam() {
        String basePath = "booking";
        Map<String, String> queryParam = Maps.of("firstname", "John");
        Response response = request.baseUri(baseURI)
                                   .basePath(basePath)
                                   .queryParams(queryParam)
                                   .get();

        System.out.println(response.asString());

    }
}
