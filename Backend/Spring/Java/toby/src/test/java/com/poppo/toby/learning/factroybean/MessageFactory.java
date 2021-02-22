package com.poppo.toby.learning.factroybean;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactory implements FactoryBean<Message> {
    private final String text;

    public MessageFactory(final String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.of(this.text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    public boolean isSingleton() {
        // 팩토리 빈으로 생성되는 빈에 대한 설정이 아니다.
        // 팩토리 빈에 대한 설정
        return false;
    }
}
