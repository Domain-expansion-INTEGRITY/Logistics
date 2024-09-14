package com.domain_expansion.integrity.user.common.entity.auditor;

import com.domain_expansion.integrity.user.common.security.UserDetailsImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName()
            .equals("anonymousUser")) {
            return Optional.empty();
        }
        String userId = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        return Optional.of(Long.parseLong(userId));
    }
}
