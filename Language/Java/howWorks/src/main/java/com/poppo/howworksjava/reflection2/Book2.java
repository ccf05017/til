package com.poppo.howworksjava.reflection2;

public class Book2 {

    public static String a = "a";

    private String b = "b";

    public Book2() {
    }

    public Book2(String b) {
        this.b = b;
    }

    private void c() {
        System.out.println("c");
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
