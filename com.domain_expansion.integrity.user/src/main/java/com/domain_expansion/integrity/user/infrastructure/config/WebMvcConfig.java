package com.domain_expansion.integrity.user.infrastructure.config;

import com.domain_expansion.integrity.user.domain.model.converter.UserRoleConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserRoleConverter());
    }
}
