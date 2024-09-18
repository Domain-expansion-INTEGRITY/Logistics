package com.domain_expansion.integrity.user.presentation.filter;

import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.jwt.JwtUtils;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.common.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtils.extractToken(request);

        if (StringUtils.hasText(tokenValue)) {
            if (!jwtUtils.checkValidJwtToken(tokenValue)) {
                throw new UserException(ExceptionMessage.AUTHORIZATION);
            }

            Claims info = jwtUtils.getBodyFromJwt(tokenValue);

            try {
                setAuthentication(Long.valueOf(info.getSubject()),
                    info.get(JwtUtils.AUTHORIZATION_KEY).toString());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    public void setAuthentication(Long userId, String role) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(userId, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(Long userId, String role) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return new UsernamePasswordAuthenticationToken(
            new UserDetailsImpl(userId, authorities, role), null,
            authorities);
    }
}
