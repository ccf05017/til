package com.poppo.spring.cloud.microservices.currencyconversionservice.auth;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
public @interface AuthorizeRequired {

    String value() default "USER";

}
