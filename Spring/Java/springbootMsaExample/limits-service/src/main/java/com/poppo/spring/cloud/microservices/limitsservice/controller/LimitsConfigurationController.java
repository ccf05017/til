package com.poppo.spring.cloud.microservices.limitsservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.poppo.spring.cloud.microservices.limitsservice.YmlConfigurations;
import com.poppo.spring.cloud.microservices.limitsservice.domain.LimitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private YmlConfigurations ymlConfigurations;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfigurations() {

        return new LimitConfiguration(ymlConfigurations.getMaximum(), ymlConfigurations.getMinimum());
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public LimitConfiguration retrieveConfiguration() {

        throw new RuntimeException("Not Available");
    }

    public LimitConfiguration fallbackRetrieveConfiguration() {

        // 문제가 있을 때 최소한의 기능으로 동작하도록 조치를 취함
        return new LimitConfiguration(9, 999);
    }
 }
