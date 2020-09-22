package com.poppo.junit.mistake.record.application;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String getHello() {
        return "hello";
    }
}
