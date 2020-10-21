package com.poppo.toby.simpleSamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorSample {
    public int calcSum(String path) throws IOException {
        return this.lineReadTemplate(
                path,
                (String line, Integer value) -> value + Integer.parseInt(line),
                0
        );
    }

    public int calcMultiply(String path) throws IOException {
        return this.lineReadTemplate(
                path,
                (String line, Integer value) -> value * Integer.parseInt(line),
                1
        );
    }

    public String concat(String path) throws IOException {
        return this.lineReadTemplate(
                path,
                (String line, String value) -> value + line,
                ""
        );
    }

    private int fileReadTemplate(String filePath, BufferedReaderCallback bufferedReaderCallback) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            return bufferedReaderCallback.doSomethingWithReader(bufferedReader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private <T> T lineReadTemplate(String filepath, LineCallback<T> lineCallback, T initValue) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            T result = initValue;
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                result = lineCallback.doSomethingWithLine(line, result);
            }

            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
