package com.domain_expansion.integrity.order.application.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BearerTokenRequestInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;  // HttpServletRequest를 주입받음

    @Override
    public void apply(RequestTemplate template) {
        String authorizationHeader = request.getHeader(
                "Authorization");  // 요청에서 Authorization 헤더 가져오기
        if (authorizationHeader != null) {
            template.header("Authorization",
                    authorizationHeader);  // Feign 요청에 Authorization 헤더 그대로 추가
        }
    }
}
