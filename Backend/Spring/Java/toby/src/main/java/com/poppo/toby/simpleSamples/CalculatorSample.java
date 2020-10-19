package com.poppo.toby.simpleSamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorSample {
    public int calcSum(String path) throws IOException {
        return this.fileReadTemplate(path, (BufferedReader bufferedReader) -> {
            int sum = 0;
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                sum += Integer.parseInt(line);
            }

            return sum;
        });
    }

    public int calcMultiply(String path) throws IOException {
        return this.fileReadTemplate(path, (BufferedReader bufferedReader) -> {
            int sum = 1;
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                sum *= Integer.parseInt(line);
            }

            return sum;
        });
    }

    public int fileReadTemplate(String filePath, BufferedReaderCallback bufferedReaderCallback) throws IOException {
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
}
