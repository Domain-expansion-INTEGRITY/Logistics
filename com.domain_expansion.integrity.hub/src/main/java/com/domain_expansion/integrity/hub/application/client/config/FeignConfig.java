package com.domain_expansion.integrity.hub.application.client.config;

import com.domain_expansion.integrity.hub.application.client.decoder.FeignClientErrorDecoder;
import com.domain_expansion.integrity.hub.application.client.interceptor.FeignClientInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignClientErrorDecoder();
    }
}