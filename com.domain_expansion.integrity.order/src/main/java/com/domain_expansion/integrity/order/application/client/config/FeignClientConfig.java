package com.domain_expansion.integrity.order.application.client.config;

import com.domain_expansion.integrity.order.application.client.decoder.FeignClientErrorDecoder;
import com.domain_expansion.integrity.order.application.client.interceptor.BearerTokenRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {

        return new FeignClientErrorDecoder();
    }

    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor(HttpServletRequest httpServletRequest) {

        return new BearerTokenRequestInterceptor(httpServletRequest);
    }
}
