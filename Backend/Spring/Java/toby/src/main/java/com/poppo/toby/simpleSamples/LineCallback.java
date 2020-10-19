package com.poppo.toby.simpleSamples;

@FunctionalInterface
public interface LineCallback {
    int doSomethingWithLine(String line, int value);
}
