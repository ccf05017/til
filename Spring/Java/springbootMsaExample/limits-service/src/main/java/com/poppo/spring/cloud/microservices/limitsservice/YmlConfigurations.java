package com.poppo.spring.cloud.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YmlConfigurations {

    @Value("${limits-service.minimum}")
    private Integer minimum;

    @Value("${limits-service.maximum}")
    private Integer maximum;

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
}
