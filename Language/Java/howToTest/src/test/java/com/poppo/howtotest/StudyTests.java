package com.poppo.howtotest;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class StudyTests {

    @Test
    void create() {

        Study study = new Study();
        assertThat(study).isNotNull();
        System.out.println("create");
    }

    @Test
    void create2() {
        System.out.println("create2");
    }

    // 반드시 static으로 선언할 것
    // return이 있어선 안됨
    // 모든 테스트 실행 이전에 한번만 실행됨
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    // @BeforeAll과 반대
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    // static일 필요 없다
    // 각각의 테스트 전에 매번 실행
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    // 각각의 테스트 이후 매번 실행
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}