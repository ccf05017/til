package com.poppo.demo.controllers.step;

import com.poppo.demo.controllers.dto.BookRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class BookAcceptanceStep {
    public static ExtractableResponse<Response> 책_목록을_요청한다() {
        return RestAssured.given().log().all().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/books").
                then().
                log().all().
                extract();
    }

    public static ExtractableResponse<Response> 책_생성됨(final String bookName, final String bookAuthor) {
        return RestAssured.given().log().all()
                .body(new BookRequest(bookName, bookAuthor))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/books")
                .then()
                .log().all()
                .extract();
    }
}
