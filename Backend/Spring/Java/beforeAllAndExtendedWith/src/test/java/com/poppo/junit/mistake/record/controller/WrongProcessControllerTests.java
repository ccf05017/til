package com.poppo.junit.mistake.record.controller;

import com.poppo.junit.mistake.record.application.TestService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)     // beforeAll 전에 실행된다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 아래 클래스의 속성들이 static 없이도 한번만 초기화 되도록 해준다.
class WrongProcessControllerTests
{
    private MockMvc mockMvc;

    @InjectMocks
    private TestController testController;

    @Mock
    private TestService testService;

    @BeforeAll
    public void setup() {
        // null 포인터 에러가 발생한다.
        // testController는 아직 초기화 된 상태가 아니기 때문
        // 이 메소드 안의 동작을 모두 마친 후에야 ExtendWith에서 Mock 객체들을 초기화한다.
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
