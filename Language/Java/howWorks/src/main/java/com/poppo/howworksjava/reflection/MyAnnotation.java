package com.poppo.howworksjava.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)     // 이게 없으면 Runtime에 안 보인다.
@Target({ElementType.TYPE, ElementType.FIELD})      // 애노테이션을 붙일 수 있는 위치 한정
public @interface MyAnnotation {

    // 값을 갖게 하고 기본값을 줄 수 있다.
    String name() default "poppo";

    int number() default 100;
}
