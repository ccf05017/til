package com.poppo.junit.mistake.record.controller;

import com.poppo.junit.mistake.record.application.TestService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 아래 클래스의 속성들이 static 없이도 한번만 초기화 되도록 해준다.
class RightProcessControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @Mock
    private TestService testService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this); // 아래에서 testController Mock을 사용하기 전에 초기화 시킨다.

        mockMvc = MockMvcBuilders.standaloneSetup(testController).alwaysDo(print()).build();
    }

    @Test
    public void testHello() throws Exception {
        given(testService.getHello()).willReturn("hello");

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hello")))
        ;
    }
}
