package com.domain_expansion.integrity.product.common.entity.auditor;

import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(((UserDetailsImpl)authentication.getPrincipal()).getUserId()));
    }
}
