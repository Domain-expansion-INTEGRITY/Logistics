package com.domain_expansion.integrity.order.application.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BearerTokenRequestInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    @Override
    public void apply(RequestTemplate template) {
        String authorizationHeader = request.getHeader(
                "Authorization");
        if (authorizationHeader != null) {
            template.header("Authorization",
                    authorizationHeader);
        }
    }
}
