package com.pingSafe.test.Customers;

import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.pingSafe.BaseSetup.TestSetup;
import com.pingSafe.Data.CustomerData;
import com.pingSafe.EndPoints.URI;
import com.pingSafe.Messages.CustomerResponse;
import com.pingSafe.POJO.Customer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HeaderConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.specification.ProxySpecification.host;

public class CustomerE2E extends TestSetup {

    RestAssuredConfig config = CurlLoggingRestAssuredConfigFactory.createConfig();


    @Test(dataProviderClass = CustomerData.class,
            dataProvider = "createCustomerData",
            description = "Create and test customer creation")
    public void createCustomer(Customer customer, CustomerResponse customerResponse) {

        Response res = customerRequest.createCustomer(customer);
        String id =new JsonPath(res.asString()).getString("id");
        System.out.println("*************************");
        res.prettyPrint();

        System.out.println("*************************");
        customerRequest.getCustomerDetailsById(id).prettyPrint();
    }

    @Test
    public void getCustomer() {

        new HeaderConfig().shouldOverwriteHeaderWithName("");

        RequestSpecification request = RestAssured.given()
                .header("x-session-token", "authorized-user")
                .header("user-agent", "Apache-HttpClient/4.5.13 (Java/20.0.2)")
                .headers("Host", "localhost:8080")
                .headers("Content-Type", "application/json")
                .headers("X-Hudson", "")
                .headers("X-Jenkins", "")
                .headers("X-Jenkins-Session", "")
                .basePath("api")
                .queryParam("id", 126);

        Response response = request.config(config).get().then().log().all().extract().response();
        Headers allHeaders = response.getHeaders();
        for (Header header : allHeaders) {
            String headerName = header.getName();
            if (headerName.startsWith("X-Hudson") || headerName.startsWith("X-Jenkins")) {
                System.out.println("Custom Header - " + header.getName() + ": " + header.getValue());
            }
        }


        String resString = response.asString();
        System.out.println("Respnse Details : " + resString);
    }


    @Test
    public void teste() {

        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = 8888;
        //  RestAssured.proxy = host("127.0.0.1").withPort(8080);
        RestAssured.given().header("Content-Type", "application/json").
                header("x-session-token", "authorized-user").log().all().
                when().get("/api?id=126").then().log().all().extract().response().prettyPrint();

    }

}
