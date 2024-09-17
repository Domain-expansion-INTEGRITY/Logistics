package com.domain_expansion.integrity.slack.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(7);
        }

        return null;
    }

    public boolean checkValidJwtToken(String jwtToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
            return true;
        } catch (Exception e) {
            log.error("에러 원인 : {} 에러 설명 : {}", e.getCause(), e.getMessage());
        }
        return false;
    }

    public Claims getBodyFromJwt(String jwtToken) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
    }
}
