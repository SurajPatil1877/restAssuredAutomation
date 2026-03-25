package http;

import com.github.javafaker.Faker;
import config.PropertyUil;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;


public abstract class BaseAPI {

    private final RequestSpecification requestSpecification;
    public final Faker FAKER = TestDataHelper.getFAKER();
    public final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;

    public BaseAPI() {
        requestSpecification = RestAssured.given().baseUri(PropertyUil.getConfig().baseURL());
    }

    protected BaseAPI setRequestBody(Object object) {
        this.requestSpecification.body(object);
        return this;
    }

    protected void setBasePath(String basePath) {
        this.requestSpecification.basePath(basePath);
    }

    protected void setPathParam(String paramKey, Object paramValue) {
        this.requestSpecification.pathParam(paramKey, paramValue);

    }

    protected BaseAPI setContentType(ContentType contentType) {
        this.requestSpecification.contentType(contentType);
        return this;
    }

    protected BaseAPI setBasicAuth(String userName, String password) {
        this.requestSpecification.auth().preemptive().basic(userName, password);
        return this;
    }

    public BaseAPI logAllRequestData() {
        this.requestSpecification.filters(new RequestLoggingFilter());
        return this;
    }

    public BaseAPI logAllSpecificRequestDetail(LogDetail logDetail) {
        this.requestSpecification.filters(new RequestLoggingFilter(logDetail));
        return this;
    }

    public BaseAPI logAllResponseData() {
        this.requestSpecification.filters(new ResponseLoggingFilter());
        return this;
    }

    public BaseAPI logAllSpecificResponseDetail(LogDetail logDetail) {
        this.requestSpecification.filters(new ResponseLoggingFilter(logDetail));
        return this;
    }

    protected Response sendRequest(Method methodType) {
        final RequestSpecification when = this.requestSpecification.when();
        return switch (methodType) {
            case GET -> when.get();
            case PUT -> when.put();
            case POST -> when.post();
            case DELETE -> when.delete();
            case PATCH -> when.patch();
            default -> throw new IllegalStateException("Unexpected value: " + methodType);
        };

    }

}
