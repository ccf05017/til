package com.poppo.toby.simpleSamples.proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {
    @Test
    void simpleTest() {
        String name = "hoo";
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );

        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO HOO");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI HOO");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANKYOU HOO");
        assertThat(proxiedHello.bayDay(name)).isEqualTo("NotGood hoo");
    }
}
