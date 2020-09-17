package com.poppo.study.multithread.multiExecute;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class MultiExecutorTests {
    @Test
    public void runMultiSynchronous() {
        List<Runnable> simpleRunners = Arrays.asList(
                new SimpleRunner(1), new SimpleRunner(2), new SimpleRunner(3),
                new SimpleRunner(4), new SimpleRunner(5), new SimpleRunner(6),
                new SimpleRunner(7), new SimpleRunner(8), new SimpleRunner(9)
        );

        MultiExecutor multiExecutor = new MultiExecutor(simpleRunners);

        multiExecutor.executeAllSynchronous();
    }

    @Test
    public void runMultiAsynchronous() {
        List<Runnable> simpleRunners = Arrays.asList(
                new SimpleRunner(1), new SimpleRunner(2), new SimpleRunner(3),
                new SimpleRunner(4), new SimpleRunner(5), new SimpleRunner(6),
                new SimpleRunner(7), new SimpleRunner(8), new SimpleRunner(9)
        );

        MultiExecutor multiExecutor = new MultiExecutor(simpleRunners);

        multiExecutor.executeAllAsynchronous();
    }

    @Test
    public void runMultiByStream() {
        // 기본적으로 비동기 처리
        List<Runnable> simpleRunners = Arrays.asList(
                new SimpleRunner(1), new SimpleRunner(2), new SimpleRunner(3),
                new SimpleRunner(4), new SimpleRunner(5), new SimpleRunner(6),
                new SimpleRunner(7), new SimpleRunner(8), new SimpleRunner(9)
        );

        MultiExecutor multiExecutor = new MultiExecutor(simpleRunners);

        multiExecutor.executeAllByStream();
    }
}
