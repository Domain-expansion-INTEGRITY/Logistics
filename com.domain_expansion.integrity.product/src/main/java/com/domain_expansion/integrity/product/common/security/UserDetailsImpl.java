package com.domain_expansion.integrity.product.common.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private String userId;
    private Collection<? extends GrantedAuthority> authorities;
    private String role;


    public UserDetailsImpl(String userId, Collection<? extends GrantedAuthority> authorities, String repRole) {
        this.userId = userId;
        this.authorities = authorities;
        this.role = repRole;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
