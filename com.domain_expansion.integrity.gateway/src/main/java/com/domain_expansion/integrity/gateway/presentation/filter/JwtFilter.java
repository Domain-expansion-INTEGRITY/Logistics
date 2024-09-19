package com.domain_expansion.integrity.gateway.presentation.filter;

import com.domain_expansion.integrity.gateway.common.exception.GlobalExceptionHandler;
import com.domain_expansion.integrity.gateway.common.message.ExceptionMessage;
import com.domain_expansion.integrity.gateway.domain.dto.UserAuthDto;
import com.domain_expansion.integrity.gateway.domain.redis.RedisDomainService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtFilter implements GlobalFilter {

    public static final String AUTH_HEADER = "Authorization";
    public static final long TOKEN_TIME = 60 * 60 * 1000L; // 60분
    public final String PREFIX_BEARER = "Bearer ";

    public final RedisDomainService redisDomainService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private SecretKey key;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // auth 및 회원가입으로 가는 요청은 막지 않는다.
        if (exchange.getRequest().getURI().getPath().startsWith("/api/v1/auth")
            // 유저에서 회원가입
            || exchange.getRequest().getURI().getPath().startsWith("/api/v1/users/sign-up")
            // swagger
            || exchange.getRequest().getURI().getPath().endsWith("api-docs")) {
            return chain.filter(exchange);
        }

        // token 추출
        String jwtToken = extractToken(exchange);

        if (jwtToken == null || !validateToken(jwtToken)) {
            return globalExceptionHandler.handleErrorResponse(exchange,
                ExceptionMessage.UNAUTHORIZED_TOKEN);
        }

        Claims claimInfo = getClaimInfoFromToken(jwtToken);
        String userId = claimInfo.getSubject();

        UserAuthDto userAuth = redisDomainService.findUserAuth(Long.valueOf(userId));

        if (userAuth == null) {
            return globalExceptionHandler.handleErrorResponse(exchange,
                ExceptionMessage.UNAUTHORIZED_TOKEN);
        }

        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(PREFIX_BEARER)) {
            return authHeader.substring(7);
        }

        return null;
    }

    private boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
            // 추가 검증 로직 가능
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaimInfoFromToken(String jwtToken) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
    }
}
