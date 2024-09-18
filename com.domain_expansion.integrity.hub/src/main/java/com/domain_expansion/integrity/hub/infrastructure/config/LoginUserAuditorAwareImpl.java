package com.domain_expansion.integrity.hub.infrastructure.config;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginUserAuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
            return Optional.of(Long.valueOf(details.getUserId()));
        }

        return Optional.empty();
    }
}
