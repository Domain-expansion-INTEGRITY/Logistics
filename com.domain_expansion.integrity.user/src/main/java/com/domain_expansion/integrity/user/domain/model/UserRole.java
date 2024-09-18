package com.domain_expansion.integrity.user.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    MASTER("ROLE_MASTER"),
    HUB_MANAGER("ROLE_HUB_MANAGER"),
    HUB_COMPANY("ROLE_HUB_COMPANY"),
    HUB_DELIVERY_MAN("ROLE_HUB_DELIVERY_MAN"),
    COMPANY_DELIVERY_MAN("ROLE_COMPANY_DELIVERY_MAN"),
    ;

    private final String authority;

    public static class Authority {

        public static final String MASTER = "ROLE_MASTER";
        public static final String HUB_MANAGER = "ROLE_HUB_MANAGER";
        public static final String HUB_COMPANY = "ROLE_HUB_COMPANY";
        public static final String HUB_DELIVERY_MAN = "ROLE_HUB_DELIVERY_MAN";
        public static final String COMPANY_DELIVERY_MAN = "ROLE_COMPANY_DELIVERY_MAN";


    }
}
