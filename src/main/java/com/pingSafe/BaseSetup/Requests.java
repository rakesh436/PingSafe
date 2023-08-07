package com.pingSafe.BaseSetup;

import com.pingSafe.EndPoints.URI;
import com.pingSafe.Helper.BaseURL;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Requests extends BaseURL {

    public Map<String, String> validHeaders() {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        header.put("x-session-token", "authorized-user");
        header.put("user-agent", "application/postscript");
        header.put("Content-Type", "application/json");
        return header;
    }

    public Response get(String path, Map<String, String> headers, Map<String, String> query) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeaders(headers)
                .setBaseUri(getBaseUrl())
                .setBasePath(path)
                .addQueryParams(query)
                .build();

        return given()
                .spec(requestSpecification)
                .log().all()
                .when()
                .get().then()
                .log().all()
                .extract().response();
    }

    public Response get(String path, Map<String, String> query) {
        return get(path, validHeaders(), query);
    }


    protected Response post(Map<String, String> headers, String path, Object payload) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .addHeaders(headers)
                .setBasePath(path)
                .setBasePath(path)
                .build();

        return given().spec(requestSpecification)
                        .log()
                        .all()
                        .when()
                        .body(payload)
                        .post()
                        .then().log().all()
                        .extract().response();

    }

    protected Response post(String path, Object payload) {
        return post(validHeaders(),path,payload);
    }
}
