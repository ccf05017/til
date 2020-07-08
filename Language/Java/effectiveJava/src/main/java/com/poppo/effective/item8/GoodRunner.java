package com.poppo.effective.item8;

public class GoodRunner {

    public static void main(String[] args) throws Exception {
        try (RoomExample roomExample = new RoomExample(7)) {
            System.out.println("Clean!");
        }
    }
}
