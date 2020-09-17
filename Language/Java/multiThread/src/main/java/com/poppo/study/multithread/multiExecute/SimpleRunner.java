package com.poppo.study.multithread.multiExecute;

public class SimpleRunner implements Runnable {
    private int number;

    public SimpleRunner(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + this.number);
    }
}
