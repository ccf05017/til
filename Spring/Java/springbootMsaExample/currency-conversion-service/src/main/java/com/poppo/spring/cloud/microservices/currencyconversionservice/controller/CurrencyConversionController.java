package com.poppo.spring.cloud.microservices.currencyconversionservice.controller;

import com.poppo.spring.cloud.microservices.currencyconversionservice.domian.CurrencyConversionBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        Map<String, String> uriVariables=  new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

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
}
