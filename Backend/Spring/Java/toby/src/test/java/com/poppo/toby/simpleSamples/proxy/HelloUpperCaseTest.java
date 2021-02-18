package com.poppo.toby.simpleSamples.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloUpperCaseTest {
    private final Hello hello = new HelloTarget();

    @Test
    void simpleTest() {
        String name = "hoo";
        HelloUpperCase helloUpperCase = new HelloUpperCase(hello);

        assertThat(helloUpperCase.sayHello(name)).isEqualTo("HELLO HOO");
        assertThat(helloUpperCase.sayHi(name)).isEqualTo("HI HOO");
        assertThat(helloUpperCase.sayThankYou(name)).isEqualTo("THANKYOU HOO");
        assertThat(helloUpperCase.bayDay(name)).isEqualTo("NotGood hoo");
    }
}
