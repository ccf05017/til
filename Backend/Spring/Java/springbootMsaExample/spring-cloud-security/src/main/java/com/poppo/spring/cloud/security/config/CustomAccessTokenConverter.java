package com.poppo.spring.cloud.security.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public Map<String, Object> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // JWT Token으로 변환해주는 곳
        Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);

        return response;
    }


}
