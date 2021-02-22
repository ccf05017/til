package com.poppo.toby.learning.factroybean;

public class Message {
    private final String text;

    // 기본 생성자를 막고 정적 팩토리 메서드를 사용하도록 구현
    // 이런 클래스도 빈으로 등록이 가능하지만, 잘못된 동작을 일으킬 가능성이 높기 때문에 팩토리 빈으로 등록하는 걸 권장한다.(무조건 해라)
    private Message(final String text) {
        this.text = text;
    }

    public static Message of(final String text) {
        return new Message(text);
    }

    public String getText() {
        return text;
    }
}
