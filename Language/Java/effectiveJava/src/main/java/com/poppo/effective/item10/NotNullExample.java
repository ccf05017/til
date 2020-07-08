package com.poppo.effective.item10;

public class NotNullExample {

    private final String test;

    public NotNullExample(String test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NotNullExample))   // 묵시적인 null 확인
            return false;
        return false;
    }
}
