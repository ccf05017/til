package com.poppo.spring.cloud.microservices.netflixzuulapigatewayserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // pre, post, error 중 선택 가능
    @Override
    public String filterType() {
        return "pre";
    }

    // 필터 여러개일 때의 우선순위
    @Override
    public int filterOrder() {
        return 1;
    }

    // 필터를 끌지 켤지 설정
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 실제 필터 동작을 구현
    @Override
    public Object run() throws ZuulException {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        logger.info("request -> {} request uri -> {}", request, request.getRequestURI());

        return null;
    }
}
