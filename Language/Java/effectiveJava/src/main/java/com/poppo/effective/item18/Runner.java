package com.poppo.effective.item18;

import java.util.Arrays;
import java.util.HashSet;

public class Runner {
    public static void main(String[] args) {
        HashSet<Object> objects = new HashSet<>();

        InstrumentedSet<Object> testSet = new InstrumentedSet<>(objects);

        // 직접 Set을 상속해서 구현했다면, addAll이 내부적으로 add를 사용하기 때문에 8이 출력됐을 것.
        testSet.add(1);
        testSet.add(2);
        testSet.addAll(Arrays.asList(1, 2, 3));

        System.out.println(testSet.getAddCount());
    }
}
