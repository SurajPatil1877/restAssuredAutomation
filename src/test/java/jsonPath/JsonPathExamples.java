package jsonPath;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;

public class JsonPathExamples {
    public static void main(String[] args) {
        String response = RestAssured.given().baseUri("https://bookcart.azurewebsites.net/api")
                                     .and().basePath("/book/")
                                     .when().get()
                                     .then().assertThat().statusCode(200)
                                     .extract().asString();

        Object object = JsonPath.read(response, "$[?(@.title =~ /.*Potter.*/i)]");
        System.out.println(object);

    }
}
