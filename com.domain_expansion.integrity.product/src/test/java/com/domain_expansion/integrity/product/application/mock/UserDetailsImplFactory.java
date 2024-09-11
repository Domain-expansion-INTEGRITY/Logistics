package com.domain_expansion.integrity.product.application.mock;

import com.domain_expansion.integrity.product.common.filter.UserRole;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImplFactory {

    private static UserDetailsImpl createUserDetails(String role) {
        return new UserDetailsImpl(
                1L,
                Arrays.stream(role.split(","))
                        .map(r -> new SimpleGrantedAuthority(r.trim()))
                        .collect(Collectors.toList()),
                role
        );
    }

    public static UserDetailsImpl createMasterUserDetails() {

        return createUserDetails(UserRole.MASTER.getRole());
    }

    public static UserDetailsImpl createHubManagerUserDetails() {

        return createUserDetails(UserRole.HUB_MANAGER.getRole());
    }

    public static UserDetailsImpl createHubCompanyUserDetails() {

        return createUserDetails(UserRole.HUB_COMPANY.getRole());
    }
}
