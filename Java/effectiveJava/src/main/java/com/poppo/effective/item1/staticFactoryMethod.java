package com.poppo.effective.item1;

public class staticFactoryMethod {

    public static Boolean valueOf(boolean b) {

        return b ? Boolean.TRUE : Boolean.FALSE;
    }
}
