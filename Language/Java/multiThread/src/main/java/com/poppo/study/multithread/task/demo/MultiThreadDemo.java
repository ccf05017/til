package com.poppo.study.multithread.task.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultiThreadDemo {
    public static void main(String[] args) {
        Runnable hellos = () -> {
            for (int i = 1; i <= 1000; i++)
                System.out.println("Hello " + i);
        };

        Runnable byes = () -> {
            for (int i = 1; i <= 1000; i++)
                System.out.println("Bye " + i);
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(hellos);
        executor.execute(byes);
    }
}
