package com.poppo;

public class App {

    static String name = "staic";

    public static void main(String args[]) {

        System.out.println("App");

        // 모든 클래스는 Object 상속 받음
        System.out.println(App.class.getSuperclass());
    }

}
