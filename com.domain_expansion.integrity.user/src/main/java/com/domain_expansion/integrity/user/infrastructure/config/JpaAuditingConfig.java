package com.domain_expansion.integrity.user.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig {

//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.empty();
//    }
}
