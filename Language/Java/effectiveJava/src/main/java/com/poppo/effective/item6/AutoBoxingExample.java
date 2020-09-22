package com.poppo.effective.item6;

public class AutoBoxingExample {

    Long sum = 0L;

    public Long sum() {
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;       // 이 부분에서 오토박싱이 발생한다.
        }
        return sum;
    }
}
