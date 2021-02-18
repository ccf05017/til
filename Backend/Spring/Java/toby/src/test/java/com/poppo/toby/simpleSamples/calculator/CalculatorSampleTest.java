package com.poppo.toby.simpleSamples.calculator;

import com.poppo.toby.simpleSamples.CalculatorSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculatorSampleTest {
    @ParameterizedTest
    @MethodSource("addTestSuccessResources")
    void addTestSuccess(int expectedResult, String resourcePath) throws IOException {
        CalculatorSample calculatorSample = new CalculatorSample();

        int sum = calculatorSample.calcSum(ResourceUtils.getFile(resourcePath).getPath());

        assertThat(sum).isEqualTo(expectedResult);
    }
    public static Stream<Arguments> addTestSuccessResources() {
        return Stream.of(
                Arguments.of(10, "classpath:numbers.txt"),
                Arguments.of(14, "classpath:numbers2.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("multiplyTestSuccessResources")
    void multiplyTestSuccess(int expectedResult, String resourcePath) throws IOException {
        CalculatorSample calculatorSample = new CalculatorSample();

        int sum = calculatorSample.calcMultiply(ResourceUtils.getFile(resourcePath).getPath());

        assertThat(sum).isEqualTo(expectedResult);
    }
    public static Stream<Arguments> multiplyTestSuccessResources() {
        return Stream.of(
                Arguments.of(24, "classpath:numbers.txt"),
                Arguments.of(120, "classpath:numbers2.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("concatTestSuccessResources")
    void concatTestSuccess(String expectedResult, String resourcePath) throws IOException {
        CalculatorSample calculatorSample = new CalculatorSample();

        String concatString = calculatorSample.concat(ResourceUtils.getFile(resourcePath).getPath());

        assertThat(concatString).isEqualTo(expectedResult);
    }
    public static Stream<Arguments> concatTestSuccessResources() {
        return Stream.of(
                Arguments.of("1234", "classpath:numbers.txt"),
                Arguments.of("2345", "classpath:numbers2.txt")
        );
    }

    @Test
    void addTestFail() {
        String notExistResource = "notExist";
        CalculatorSample calculatorSample = new CalculatorSample();

        assertThatThrownBy(() -> calculatorSample.calcSum(ResourceUtils.getFile(notExistResource).getPath()))
                .isInstanceOf(IOException.class);
    }
}
