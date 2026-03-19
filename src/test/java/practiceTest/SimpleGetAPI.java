package practiceTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.groovy.util.Maps;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class SimpleGetAPI {
    String baseURI = "https://automationexercise.com";
    String basePath = "/api/productsList";

    @Test
    public void simpleProductListAPI() {
        // init the request spec class
        RequestSpecification request = RestAssured.given();

        Response response = request.baseUri(baseURI)
                                   .basePath(basePath)
                                   .contentType(ContentType.JSON)
                                   .get();

        String string = response.asString();
        System.out.println(string);


    }

    @Test
    public void defineHeaders() {
        Map<String, Object> headersMap = Maps.of(
                "test-header1", "test-value1",
                "test-header2", "test-value2",
                "test-header3", "test-value3"
        );
        //Use the inbuilt Header class of RestAssured
        Header header1 = new Header("test-header1", "test-value");
        Header header2 = new Header("test-header1", "test-value");
        Headers headers = new Headers(header1, header2);

        List<Header> header3 = List.of(header1, header2);
        Headers headersList = new Headers(header3);
        // init the request spec class
        RequestSpecification request = RestAssured.given();

        Response response = request.baseUri(baseURI)
                                   .basePath(basePath)
                                   .contentType(ContentType.JSON)
                                   //custom name and value
                                   .header("test-header", "test-value")
                                   //multiple headers with key value
                                   .headers("test-header", "test-value", "test-header1", "test-value")
                                   //using Map
                                   .headers(headersMap)
                                   //inbuiltHeaders
                                   .header(header1)
                                   //multiple headers
                                   .headers(headers)
                                   .headers(headersList)
                                   .get();

        String string = response.asString();
        System.out.println(string);

    }
}
