package com.poppo.toby.simpleSamples.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicProxyTest {
    @Test
    void useDynamicProxy() {
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

    @Test
    void useProxyFactoryBean() {
        String name = "hoo";
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.addMethodName("say*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(nameMatchMethodPointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) proxyFactoryBean.getObject();

        assertThat(proxiedHello.sayHello(name)).isEqualTo("HELLO HOO");
        assertThat(proxiedHello.sayHi(name)).isEqualTo("HI HOO");
        assertThat(proxiedHello.sayThankYou(name)).isEqualTo("THANKYOU HOO");
        assertThat(proxiedHello.bayDay(name)).isEqualTo("NotGood hoo");
    }
}
