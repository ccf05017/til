package com.poppo.spring.cloud.microservices.currencyconversionservice.security;

import com.poppo.spring.cloud.microservices.currencyconversionservice.filter.UserContext;
import com.poppo.spring.cloud.microservices.currencyconversionservice.filter.UserContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest httpRequest,
            byte[] bytes,
            ClientHttpRequestExecution clientHttpRequestExecution)
            throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        System.out.println("#######################################################");
        System.out.println("hello i'm on");
        System.out.println(UserContextHolder.getContext().getAuthToken());

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
