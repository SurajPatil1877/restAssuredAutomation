package section5;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class BasicAndDigestAuthTests {
    private final String baseURI = "https://restful-booker.herokuapp.com";
    private final String challengedBasicAuthURL = "https://the-internet.herokuapp.com";
    private final String digestAuthURL = "https://the-internet.herokuapp.com";


    @Test
    public void simpleHTTPDeleteWithPreemptiveBasicAuth() {
        getRequestSpecification(baseURI)
                .and().basePath("/booking/{bookingId}")
                // we have to pass credentials to the api but usually APIs do not challenge our capability to login
                .and().auth().preemptive().basic("admin", "password123")
                .and().pathParam("bookingId", 1)
                .when().delete();
    }

    @Test
    public void challengedBasicAuth() {
        Response challengedAuthAPIResponse = getRequestSpecification(challengedBasicAuthURL)
                .and().basePath("/basic_auth")
                //restassured will provide credentials when server will ask for it
                .and().auth().basic("admin", "admin")
                .when().get();
        challengedAuthAPIResponse.prettyPrint();
    }

    @Test
    public void challengedDigestAuth() {
        Response challengedAuthAPIResponse = getRequestSpecification(digestAuthURL)
                .and().basePath("/digest_auth")
                //restassured will provide credentials when server will ask for it
                .and().auth().digest("admin", "admin")
                .when().get();
        challengedAuthAPIResponse.prettyPrint();
    }


    @Test
    public void simpleHTTPDeleteWithCustomAuthHeader() {
        getRequestSpecification(baseURI)
                .and().basePath("/booking/{bookingId}")
                .and().header("Cookie", " token=7fd0ec83a7622f2")
                .and().pathParam("bookingId", 12)
                .when().delete();
    }


    private RequestSpecification getRequestSpecification(String baseURL) {
        return RestAssured.given()
                          .and().baseUri(baseURL)
                          .and().filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }


}
