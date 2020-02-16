package com.poppo.spring.cloud.microservices.currencyconversionservice.applications;

import com.poppo.spring.cloud.microservices.currencyconversionservice.domian.CurrencyConversionBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final OAuth2RestTemplate restTemplate;

    public CurrencyConversionBean getCurrencyConversionBean(String from, String to) {
        ResponseEntity<CurrencyConversionBean> forEntity = restTemplate.getForEntity(
                "http://localhost:8765/currency-exchange-service/currency-exchange/from/"
                        + from
                        + "/to/"
                        + to,
                CurrencyConversionBean.class);

        return forEntity.getBody();
    }
}
