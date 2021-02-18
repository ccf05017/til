package com.poppo.toby.simpleSamples.proxy;

public class HelloTarget implements Hello {
    @Override
    public String sayHello(final String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHi(final String name) {
        return "Hi " + name;
    }

    @Override
    public String sayThankYou(final String name) {
        return "ThankYou " + name;
    }

    @Override
    public String bayDay(final String name) {
        return "NotGood " + name;
    }
}
