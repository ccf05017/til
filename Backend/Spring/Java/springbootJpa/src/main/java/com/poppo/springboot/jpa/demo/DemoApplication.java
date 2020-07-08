package com.poppo.springboot.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

// @EnableJpaRepositories(basePackages = "com.poppo.springboot.jpa.demo.domain.repository") <- 이게 생략되어 있다.
@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {

        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
