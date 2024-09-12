package com.domain_expansion.integrity.product.common.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private Long userId;
    private Collection<? extends GrantedAuthority> authorities;
    private String role;


    public UserDetailsImpl(Long userId, Collection<? extends GrantedAuthority> authorities, String repRole) {
        this.userId = userId;
        this.authorities = authorities;
        this.role = repRole;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getPassword() {

        throw new UnsupportedOperationException("UserDetailsImpl#getPassword() not implemented");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
