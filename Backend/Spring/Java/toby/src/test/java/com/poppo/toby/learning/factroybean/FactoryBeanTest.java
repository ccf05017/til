package com.poppo.toby.learning.factroybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FactoryBeanExampleConfiguration.class)
@DirtiesContext
public class FactoryBeanTest {
    @Autowired
    private Message message;

    // 팩토리 빈으로 생성되는 타입의 앞에 &를 붙이면 팩토리빈을 불러올 수 있다.
    @Resource(name = "&message")
    private MessageFactory messageFactory;

    @Test
    void getMessageTest() {
        assertThat(message.getText()).isEqualTo("hello");
    }

    @Test
    void getFactoryTest() {
        assertThat(messageFactory).isInstanceOf(MessageFactory.class);
    }
}
