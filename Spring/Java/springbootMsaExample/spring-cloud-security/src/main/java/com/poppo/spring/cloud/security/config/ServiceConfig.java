package com.poppo.spring.cloud.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceConfig {
    // @Value("${signing.key}")
    private String jwtSigningKey = "1923jwslkjadskfln2dsj1902juiwdpj1390qdjip1jcp1j019192kwklslk11";


    public String getJwtSigningKey() {
        return jwtSigningKey;
    }
}
