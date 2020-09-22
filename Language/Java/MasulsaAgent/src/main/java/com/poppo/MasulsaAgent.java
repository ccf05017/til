package com.poppo;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class MasulsaAgent {

    // main이 실행되게 전에 실행됨
    public static void premain(String agentArgs, Instrumentation inst) {

        // method 중 pullOut를 찾아서 고정값을 출력하도록 변경해버림
        new AgentBuilder.Default()
            .type(ElementMatchers.any())
            .transform((builder, typeDescription, classLoader, javaModule)
                    -> builder.method(named("pullOut")).intercept(FixedValue.value("Rabbit!")))
                .installOn(inst);
        ;
    }
}
