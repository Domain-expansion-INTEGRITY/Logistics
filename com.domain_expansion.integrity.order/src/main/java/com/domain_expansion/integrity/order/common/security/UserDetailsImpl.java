package com.domain_expansion.integrity.order.common.security;

import com.domain_expansion.integrity.order.common.filter.UserRole;
import java.util.Collection;
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

    public boolean isMaster() {

        return role.equals(UserRole.MASTER.getRole());
    }

    public boolean isHubCompany() {

        return role.equals(UserRole.HUB_COMPANY.getRole());
    }

    public boolean isCompanyDeliveryMan() {

        return role.equals(UserRole.COMPANY_DELIVERY_MAN.getRole());
    }

    public boolean isHubDeliveryMan() {

        return role.equals(UserRole.HUB_DELIVERY_MAN.getRole());
    }

    public boolean isHubManager() {

        return role.equals(UserRole.HUB_MANAGER);
    }
}
