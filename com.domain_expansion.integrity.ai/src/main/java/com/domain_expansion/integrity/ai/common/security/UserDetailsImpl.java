package com.domain_expansion.integrity.ai.common.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserDetailsImpl(
    Long userId,
    Collection<? extends GrantedAuthority> authorities,
    String role

) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("UserDetailsImpl#Password() not implemented");
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.userId);
    }

}
