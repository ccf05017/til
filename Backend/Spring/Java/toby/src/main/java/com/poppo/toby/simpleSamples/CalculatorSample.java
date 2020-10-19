package com.poppo.toby.simpleSamples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalculatorSample {
    public int calcSum(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        int sum = 0;
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
            sum += Integer.parseInt(line);
        }

        bufferedReader.close();
        return sum;
    }
}
