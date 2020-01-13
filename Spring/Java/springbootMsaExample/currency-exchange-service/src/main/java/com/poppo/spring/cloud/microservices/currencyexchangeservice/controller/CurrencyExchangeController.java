package com.poppo.spring.cloud.microservices.currencyexchangeservice.controller;

import com.poppo.spring.cloud.microservices.currencyexchangeservice.domain.ExchangeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to
    ) {

        return ExchangeValue.builder()
                .id(1L)
                .from(from)
                .to(to)
                .conversionMultiple(BigDecimal.valueOf(1000))
                .port(Integer.parseInt(environment.getProperty("local.server.port")))
                .build();
    }
}
