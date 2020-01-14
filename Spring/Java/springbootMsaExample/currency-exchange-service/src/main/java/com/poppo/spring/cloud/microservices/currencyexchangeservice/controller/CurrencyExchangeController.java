package com.poppo.spring.cloud.microservices.currencyexchangeservice.controller;

import com.poppo.spring.cloud.microservices.currencyexchangeservice.domain.ExchangeValue;
import com.poppo.spring.cloud.microservices.currencyexchangeservice.domain.repository.ExchangeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final Environment environment;
    private final ExchangeValueRepository exchangeValueRepository;  // 간단하니까 쓰는 안좋은 구현(원래 서비스 거쳐야 한다)

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to
    ) {

        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
        exchangeValue.changeToRunningPort(environment);

        return exchangeValue;
    }
}
