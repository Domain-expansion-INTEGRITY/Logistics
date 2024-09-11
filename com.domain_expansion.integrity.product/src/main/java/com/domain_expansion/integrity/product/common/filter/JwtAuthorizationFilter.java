package com.domain_expansion.integrity.product.common.filter;

import com.domain_expansion.integrity.product.common.jwt.JwtUtils;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtils.getTokenFromRequest(request);

        if (StringUtils.hasText(token)) {
            token = jwtUtils.substringToken(token);

            if (!jwtUtils.checkValidJwtToken(token)) {
                throw new JwtException("Token Validate Error");
            }

            Claims claims = jwtUtils.getBodyFromJwt(token);

            setAuthentication(claims);
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(Claims claims) {

        String userId = claims.getSubject();
        String roles = claims.get(JwtUtils.AUTHORIZATION_KEY).toString();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .collect(Collectors.toList());

        UserDetailsImpl userDetails = new UserDetailsImpl(userId, authorities, roles);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
