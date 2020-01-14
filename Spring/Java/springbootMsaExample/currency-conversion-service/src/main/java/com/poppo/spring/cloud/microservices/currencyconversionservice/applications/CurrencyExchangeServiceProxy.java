package com.poppo.spring.cloud.microservices.currencyconversionservice.applications;

import com.poppo.spring.cloud.microservices.currencyconversionservice.domian.CurrencyConversionBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange-service", url="localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retrieveExchangeValue(
            @PathVariable("from") String from, @PathVariable("to") String to);
}
