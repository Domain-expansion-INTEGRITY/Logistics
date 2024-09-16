package com.domain_expansion.integrity.delivery.application.client.config;

import com.domain_expansion.integrity.delivery.application.client.decoder.FeignClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {

        return new FeignClientErrorDecoder();
    }
}
