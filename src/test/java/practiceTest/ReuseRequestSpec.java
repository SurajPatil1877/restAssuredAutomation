package practiceTest;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.groovy.util.Maps;
import org.testng.annotations.Test;

import java.util.Map;

public class ReuseRequestSpec {
    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final RequestSpecification request = RestAssured.given()
                                                            .and().baseUri(this.baseURI)
                                                            .and().filters(new RequestLoggingFilter(), new RequestLoggingFilter());

    @Test
    public void prettyPrintResponse() {
        String basePath = "/booking/{bookingId}";
        Response response = this.request
                .basePath(basePath)
                .pathParam("bookingId", 2)
                .get();
        //pretty print the response
        response.prettyPrint();
    }

    @Test
    public void getAPIWithQueryParam() {
        String basePath = "booking";
        Map<String, String> queryParam = Maps.of("firstname", "John");
        Response response = this.request
                .basePath(basePath)
                .queryParams(queryParam)
                .get();
        System.out.println(response.asString());
    }
}
