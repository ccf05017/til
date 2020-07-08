package com.poppo.spring.cloud.microservices.currencyconversionservice.controller;

import com.poppo.spring.cloud.microservices.currencyconversionservice.applications.RestTemplateService;
import com.poppo.spring.cloud.microservices.currencyconversionservice.domian.CurrencyConversionBean;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final RestTemplateService restTemplateService;
//    private final CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        // REST Template을 활용한 무식한 구현 방법
        // Feign으로 간단하게 해결 가능하다.
        Map<String, String> uriVariables=  new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",      // Exchange Srrver
                CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();
        logger.info("{}", response);

        return CurrencyConversionBean.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .port(response.getPort())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) { // feign 버전
        CurrencyConversionBean response = restTemplateService.getCurrencyConversionBean(from, to);

        logger.info("{}", response);

        return CurrencyConversionBean.builder()
                .id(response.getId())
                .from(from)
                .to(to)
                .conversionMultiple(response.getConversionMultiple())
                .port(response.getPort())
                .quantity(quantity)
                .totalCalculatedAmount(quantity.multiply(response.getConversionMultiple()))
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
