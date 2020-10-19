package com.poppo.toby.simpleSamples;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorSampleTest {
    @Test
    void addTest() throws IOException {
        int expectedResult = 10;
        CalculatorSample calculatorSample = new CalculatorSample();
        
        int sum = calculatorSample.calcSum(ResourceUtils.getFile("classpath:numbers.txt").getPath());

        assertThat(sum).isEqualTo(expectedResult);
    }
}
