package com.poppo.study.multithread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EduTests {
    @DisplayName("현재 JVM의 가용 가능한 스레드 개수를 알아낼 수 있다.")
    @Test
    void getThreadNumber() {
        int threadNumber = Runtime.getRuntime().availableProcessors(); // 실행하는 PC 환경에 따라 달라질 것
        assertThat(threadNumber).isEqualTo(12);
    }
}
