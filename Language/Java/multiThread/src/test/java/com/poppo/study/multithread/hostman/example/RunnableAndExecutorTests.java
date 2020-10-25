package com.poppo.study.multithread.hostman.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class RunnableAndExecutorTests {
    @DisplayName("현재 PC에서 사용 가능한 스레드 수를 확인할 수 있다.")
    @Test
    void howManyThreadTest() {
        int processors = Runtime.getRuntime().availableProcessors();

        System.out.println(processors);
    }

    @DisplayName("간단한 멀티스레드 예시")
    @Test
    void simpleExample() {
        Runnable helloRunnable = () -> {
            for (int i = 1; i <= 1000; i++) {
                System.out.println("Hello " + i);
            }
        };
        Runnable goodbyeRunnable = () -> {
            for (int i = 1; i <= 1000; i++) {
                System.out.println("GoodBye " + i);
            }
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(helloRunnable);
        executor.execute(goodbyeRunnable);
    }
}
