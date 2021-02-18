package com.poppo.toby.learning;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionLearningTest {
    private String target = "test target";

    @Test
    void simpleLengthTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int expectedSize = 11;
        assertThat(target.length()).isEqualTo(expectedSize);

        Method lengthMethod = String.class.getMethod("length");
        assertThat(lengthMethod.invoke(target)).isEqualTo(expectedSize);
    }

    @Test
    void charAtTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        char expectedChar = 't';
        assertThat(target.charAt(0)).isEqualTo(expectedChar);

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertThat(charAtMethod.invoke("target", 0)).isEqualTo(expectedChar);
    }
}
