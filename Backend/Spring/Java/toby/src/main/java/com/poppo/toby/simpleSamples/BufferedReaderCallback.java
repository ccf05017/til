package com.poppo.toby.simpleSamples;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface BufferedReaderCallback {
    int doSomethingWithReader(BufferedReader bufferedReader) throws IOException;
}
