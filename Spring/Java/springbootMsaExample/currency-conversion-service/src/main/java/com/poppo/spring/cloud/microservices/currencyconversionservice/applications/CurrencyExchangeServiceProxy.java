package com.poppo.spring.cloud.microservices.currencyconversionservice.applications;

import com.poppo.spring.cloud.microservices.currencyconversionservice.domian.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name="currency-exchange-service", url="localhost:8000")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="netflix-zuul-api-gateway-server")    // API GW를 통해서 간접적으로만 접근하도록 변경
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

//    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")   // 이 부분도 API GW를 통하도록 변경
    CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
