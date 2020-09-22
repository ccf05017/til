package com.poppo.effective.item3;

import java.io.Serializable;

public class SingletonExample2 implements Serializable {

    private static final SingletonExample2 INSTANCE = new SingletonExample2();

    private SingletonExample2() {}

    public static SingletonExample2 getInstance() { return INSTANCE; }

    protected Object readResolve() {

        return INSTANCE;
    }
}
