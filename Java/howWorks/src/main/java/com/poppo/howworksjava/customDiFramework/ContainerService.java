package com.poppo.howworksjava.customDiFramework;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ContainerService {

    public static <T> T getObject(Class<T> classType) {

        T instance = createInstance(classType);

        // field injection 여부 확인
        Arrays.stream(classType.getDeclaredFields()).forEach(f -> {
            if (f.getAnnotation(Inject.class) != null) {

                // field에 넣을 알맞는 instance 생성
                Object fieldInstance = createInstance(f.getType());
                f.setAccessible(true);
                try {
                    // 생성했던 instance의 field로 생성해둔 instance 삽입
                    f.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
