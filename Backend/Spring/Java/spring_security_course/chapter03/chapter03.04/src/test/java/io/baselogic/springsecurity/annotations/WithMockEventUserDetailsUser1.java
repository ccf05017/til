package io.baselogic.springsecurity.annotations;

import io.baselogic.springsecurity.domain.AppUser;
import org.springframework.security.core.context.SecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to set {@link AppUser} to the {@link SecurityContext}
 */
@Retention(RetentionPolicy.RUNTIME)
@WithMockEventUserDetails(username = "user1@baselogic.com", password = "user1", name = "USER1")
public @interface WithMockEventUserDetailsUser1 { }
