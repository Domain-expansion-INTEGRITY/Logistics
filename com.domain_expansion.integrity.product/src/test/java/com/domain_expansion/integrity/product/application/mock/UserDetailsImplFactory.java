package com.domain_expansion.integrity.product.application.mock;

import com.domain_expansion.integrity.product.common.filter.UserRole;
import com.domain_expansion.integrity.product.common.security.UserDetailsImpl;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImplFactory {

    public static UserDetailsImpl createMasterUserDetails() {

        String role = UserRole.MASTER.getRole();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                Arrays.stream(role.split(","))
                        .map(r -> new SimpleGrantedAuthority(r.trim()))
                        .collect(Collectors.toList()),
                role
        );

        return userDetails;
    }

    public static UserDetailsImpl createHubManagerUserDetails() {

        String role = UserRole.HUB_MANAGER.getRole();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                Arrays.stream(role.split(","))
                        .map(r -> new SimpleGrantedAuthority(r.trim()))
                        .collect(Collectors.toList()),
                role
        );

        return userDetails;
    }

    public static UserDetailsImpl createHubCompanyUserDetails() {

        String role = UserRole.HUB_COMPANY.getRole();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                Arrays.stream(role.split(","))
                        .map(r -> new SimpleGrantedAuthority(r.trim()))
                        .collect(Collectors.toList()),
                role
        );

        return userDetails;
    }
}
