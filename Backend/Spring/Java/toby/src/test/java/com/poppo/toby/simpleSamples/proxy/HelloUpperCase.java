package com.poppo.toby.simpleSamples.proxy;

// 일반적인 프록시의 모든 문제점을 갖고 있다.
// 1. 인터페이스의 모든 메서드를 다 구현해야 함 -> 귀찮다.
// 2. 모든 메서드 끝에 대문자를 만드는 부가 코드가 중복되고 있다.
public class HelloUpperCase implements Hello {
    private final Hello hello;

    public HelloUpperCase(final Hello hello) {
        this.hello = hello;
    }

    @Override
    public String sayHello(final String name) {
        return hello.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(final String name) {
        return hello.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(final String name) {
        return hello.sayThankYou(name).toUpperCase();
    }

    @Override
    public String bayDay(final String name) {
        return hello.bayDay(name);
    }
}
