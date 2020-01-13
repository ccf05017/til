package com.poppo.spring.cloud.microservices.limitsservice.controller;

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
 }
