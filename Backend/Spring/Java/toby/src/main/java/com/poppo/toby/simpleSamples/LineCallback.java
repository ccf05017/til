package com.poppo.toby.simpleSamples;

@FunctionalInterface
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
