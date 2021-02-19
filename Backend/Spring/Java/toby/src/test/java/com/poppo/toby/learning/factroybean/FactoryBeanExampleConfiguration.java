package com.poppo.toby.learning.factroybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanExampleConfiguration {
    @Bean(name = "message")
    public MessageFactory messageFactory() {
        return new MessageFactory("hello");
    }

    @Bean
    public Message message() throws Exception {
        return messageFactory().getObject();
    }
}
