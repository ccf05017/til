package com.poppo.effective.item10;

public class BadEqualsExampleRunner {

    public static void main(String[] args) {
        BadEqualExample example = new BadEqualExample("hello");
        String s = "hello";

        System.out.println(example.equals(s));
        System.out.println(s.equals(example));  // String은 이런 애 모르기 때문에 실패한다.
    }
}
