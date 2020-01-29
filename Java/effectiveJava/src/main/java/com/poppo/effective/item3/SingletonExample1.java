package com.poppo.effective.item3;

import java.io.Serializable;

public class SingletonExample1 implements Serializable {

    public static final SingletonExample1 INSTANCE = new SingletonExample1();

    // 생성자는 무조건 감춰야 한다.
    private SingletonExample1() {}

    protected Object readResolve() {

        return INSTANCE;
    }
}
