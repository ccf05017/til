package com.poppo.toby.simpleSamples.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTargetTest {
    @Test
    void simpleTest() {
        Hello hello = new HelloTarget();
        String name = "hoo";

        assertThat(hello.sayHello(name)).isEqualTo("Hello " + name);
        assertThat(hello.sayHi(name)).isEqualTo("Hi " + name);
        assertThat(hello.sayThankYou(name)).isEqualTo("ThankYou " + name);
        assertThat(hello.bayDay(name)).isEqualTo("NotGood " + name);
    }
}
