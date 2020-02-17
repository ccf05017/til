package com.poppo.spring.cloud.microservices.currencyconversionservice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            HttpRequest httpRequest,
            byte[] bytes,
            ClientHttpRequestExecution clientHttpRequestExecution
    ) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        URI uri = httpRequest.getURI();
        System.out.println("##############################################");
        System.out.println(uri);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        response.getHeaders().add("foo", "bar");

        return response;
    }
}
