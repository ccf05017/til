package com.poppo.effective.item11;

public class HashCodeRunner {
    public static void main(String[] args) {
        PhoneNumber phoneNumber1 = new PhoneNumber(33, 44, 55);
        PhoneNumber phoneNumber2 = new PhoneNumber(33, 44, 55);

        System.out.println(phoneNumber1 == phoneNumber2);
        System.out.println(phoneNumber1.equals(phoneNumber2));
        System.out.println(phoneNumber1.hashCode() == phoneNumber2.hashCode());
    }
}
