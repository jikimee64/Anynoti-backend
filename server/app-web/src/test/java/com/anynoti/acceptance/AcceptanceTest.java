package com.anynoti.acceptance;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

//@Import({AuthServiceConfig.class})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
public abstract class AcceptanceTest {

    //TODO: 2월 11일 1시 40분 발급, 한달짜리 테스트 토큰
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJwcm92aWRlcklkXCI6XCIxMDA4ODU1MDgwNTMxNzE2ODI1MTdcIixcInByb3ZpZGVyVHlwZVwiOlwiR09PR0xFXCJ9IiwiaWF0IjoxNjQ0NTU0NTE4LCJleHAiOjE2NDQ2NDA5MTh9.adOQuPLG7-auer45nhe1Nf6ED7eiEroa23FhwrldZS0NK8tBcEoY5f-FUlp6bN3Rsbs7CzqaikRcm7VU5aunTQ";

    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    int port;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> callApiGet(String url, Map<String, String> params) {
        return this.doGet(
            url,
            params
        );
    }

    protected <T> ExtractableResponse<Response> callApiPost(String url, T request, Class<T> clazz) {
        return this.doPost(
            url,
            request
        );
    }

    private <T> ExtractableResponse<Response> doGet(String path, Map<String, String> params) {
        return given().log().all()
            .headers(getHeaders())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .and()
            .when()
            .queryParams(params)
            .get(path)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    private <T> ExtractableResponse<Response> doPost(String path, T request) {
        return given().log().all()
            .headers(getHeaders())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .and()
            .body(request)
            .when()
            .post(path)
            .then()
            .log().all()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }

    private Headers getHeaders() {
        Header authToken = new Header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN);
        List<Header> list = new ArrayList<Header>();
        list.add(authToken);
        return new Headers(list);
    }

}