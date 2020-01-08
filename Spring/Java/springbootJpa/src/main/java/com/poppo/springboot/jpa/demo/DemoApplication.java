package com.poppo.springboot.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableJpaRepositories(basePackages = "com.poppo.springboot.jpa.demo.domain.repository") <- 이게 생략되어 있다.
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
