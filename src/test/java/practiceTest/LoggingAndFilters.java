package practiceTest;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class LoggingAndFilters {
    RequestSpecification request = RestAssured.given();
    String baseURI = "https://restful-booker.herokuapp.com";

    @Test
    public void prettyPrintResponse() {
        String basePath = "/booking/{bookingId}";
        Response response = request.baseUri(baseURI)
                                   .basePath(basePath)
                                   .pathParam("bookingId", 2)
                                   .get();


        //pretty print the response

        response.prettyPrint();
    }

    @Test
    public void restAssuredFiltersResponse() {
        String basePath = "/booking/{bookingId}";
        //create a new request logging filter
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();

        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter();

        request.baseUri(baseURI)
               .basePath(basePath)
               .pathParam("bookingId", 2)
               .filters(requestLoggingFilter, responseLoggingFilter)
               .get();
    }

    @Test
    public void restAssuredFiltersWithLogDetailsResponse() {
        String basePath = "/booking/{bookingId}";
        //create a new request logging filter
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(LogDetail.PARAMS);
        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(LogDetail.BODY);
        request.baseUri(baseURI)
               .basePath(basePath)
               .pathParam("bookingId", 2)
               .filters(requestLoggingFilter, responseLoggingFilter)
               .get();
    }
}
