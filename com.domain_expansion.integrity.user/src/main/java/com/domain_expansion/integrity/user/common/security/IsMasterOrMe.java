package com.domain_expansion.integrity.user.common.security;

import com.domain_expansion.integrity.user.domain.model.UserRole.Authority;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated()")
@PostAuthorize("hasRole('" + Authority.MASTER + "') "
    + "or T(com.domain_expansion.integrity.user.common.security.PostAuthUtils).checkSameUser(principal.userId, returnObject)")
public @interface IsMasterOrMe {

}
