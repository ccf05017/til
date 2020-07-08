package com.poppo.howworksjava.reflection2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Book2Runner {

    public static void main(String[] args)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        // reflection의 시작
        Class<?> bookClass = Class.forName("com.poppo.howworksjava.reflection2.Book2");

        // reflection으로 생성자 실행하기
        Constructor<?> bookConstructorNoArgs = bookClass.getConstructor(null);
        Book2 book2NoArgs = (Book2) bookConstructorNoArgs.newInstance();
        System.out.println(book2NoArgs);
        Constructor<?> bookConstructor = bookClass.getConstructor(String.class);
        Book2 book2 = (Book2) bookConstructor.newInstance("myBook2");
        System.out.println(book2);

        // static field 가져오기
        Field a = Book2.class.getDeclaredField("a");
        System.out.println(a.get(null));
        a.set(null, "BB");
        System.out.println(a.get(null));

        // non-static field 가져오기
        // private이고 뭐고 쌩까고 가져올 수 있다.
        Field b = Book2.class.getDeclaredField("b");
        b.setAccessible(true);
        System.out.println(b.get(book2));
        // 값 변경도 가능하다.
        b.set(book2, "not my book");
        System.out.println(b.get(book2));

        // private method 가져오기
        Method c = Book2.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(book2);

        // public method 가져오기
        Method sum = Book2.class.getDeclaredMethod("sum", int.class, int.class);
        int result = (int) sum.invoke(book2, 1, 2);
        System.out.println(result);
    }
}
