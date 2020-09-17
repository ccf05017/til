package com.poppo.study.multithread.multiExecute;

import java.util.List;
import java.util.stream.Collectors;

public class MultiExecutor {
    List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAllSynchronous() {
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public void executeAllAsynchronous() {
        List<Thread> threads = tasks.stream()
                .map(Thread::new)
                .collect(Collectors.toList());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void executeAllByStream() {
        List<Thread> threads = tasks.stream()
                .map(Thread::new)
                .collect(Collectors.toList());

        threads.forEach(Thread::start);
    }
}
