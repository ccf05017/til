package com.poppo.spring.cloud.microservices.currencyconversionservice;

import brave.sampler.Sampler;
import com.poppo.spring.cloud.microservices.currencyconversionservice.filter.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.servlet.Filter;

@SpringBootApplication
@EnableFeignClients("com.poppo.spring.cloud.microservices.currencyconversionservice")
@EnableDiscoveryClient
@EnableResourceServer
public class CurrencyConversionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConversionServiceApplication.class, args);
    }

    @Bean
    public Sampler defaultSampler() {

        return Sampler.ALWAYS_SAMPLE;
    }

    @Bean
    public Filter userContextFilter() {

        return new UserContextFilter();
    }
}
