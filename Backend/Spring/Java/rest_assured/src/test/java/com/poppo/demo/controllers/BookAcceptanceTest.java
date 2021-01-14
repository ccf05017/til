package com.poppo.demo.controllers;

import com.poppo.demo.AcceptanceTest;
import com.poppo.demo.controllers.dto.BookRequest;
import com.poppo.demo.controllers.dto.BookResponse;
import com.poppo.demo.domain.Book;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.poppo.demo.controllers.step.BookAcceptanceStep.책_목록을_요청한다;
import static com.poppo.demo.controllers.step.BookAcceptanceStep.책_생성됨;
import static org.assertj.core.api.Assertions.assertThat;

class BookAcceptanceTest extends AcceptanceTest {
    @Test
    void getBooksWithoutBookTest() {
        /*
            when: 책 목록을 요청한다.
            then: 책 목록에 아무것도 없다.
         */
        int expectedSize = 0;

        // when
        ExtractableResponse<Response> response = 책_목록을_요청한다();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<Book> books = new ArrayList<>(response.jsonPath().getList("", Book.class));
        assertThat(books.size()).isEqualTo(expectedSize);
    }

    @Test
    void getBooksWithBookTest() {
        /*
            given: 책이 생성되어 있다.
            when: 책 목록을 요청한다.
            then: 책이 한 권 있다.
        */
        // given
        ExtractableResponse<Response> bookCreatedResponse = 책_생성됨("오브젝트", "조영호");
        long bookId = Long.parseLong(bookCreatedResponse.header("Location").split("/")[2]);

        // when
        ExtractableResponse<Response> response = 책_목록을_요청한다();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<BookResponse> bookResponses = new ArrayList<>(response.jsonPath().getList(".", BookResponse.class));
        assertThat(bookResponses).hasSize(1);
        List<Long> bookIds = response.jsonPath().getList(".", BookResponse.class).stream()
                .map(BookResponse::getId)
                .collect(Collectors.toList());
        assertThat(bookIds).contains(bookId);
    }
}