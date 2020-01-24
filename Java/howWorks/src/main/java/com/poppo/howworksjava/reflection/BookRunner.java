package com.poppo.howworksjava.reflection;

import java.util.Arrays;

public class BookRunner {
    public static void main(String[] args) throws ClassNotFoundException {

        // Class 인스턴스를 얻는 세가지 방법
        Class<Book> bookClass = Book.class;

        Book book = new Book();
        Class<? extends Book> classByObject = book.getClass();

        Class<?> classByClassName = Class.forName("com.poppo.howworksjava.reflection.Book");

        // public만 보임
        System.out.println("클래스의 공개된 필드 조회");
        Arrays.stream(bookClass.getFields()).forEach(System.out::println);

        // 전부 다 보임
        System.out.println("클래스의 모든 필드 조회");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

        // 필드의 값에 접근해보자
        System.out.println("클래스 필드의 값 조회");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try {
                f.setAccessible(true);           // 접근 지시자 무시
                System.out.println(f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        // 메소드를 가져오자
        System.out.println("메서드를 보자");
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

        // 상위 클래스를 보자
        System.out.println("부모 클래스를 보자");
        System.out.println(MyBook.class.getSuperclass());

        // 인터페이스를 보자
        System.out.println("인터페이스를 보자");
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
    }
}
