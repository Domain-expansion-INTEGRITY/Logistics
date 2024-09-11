package com.domain_expansion.integrity.product.common.entity.auditor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class JpaAuditingConfig {

}
