package com.domain_expansion.integrity.delivery.common.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    MASTER("ROLE_MASTER"),
    HUB_MANAGER("ROLE_HUB_MANAGER"),
    HUB_COMPANY("ROLE_HUB_COMPANY"),
    HUB_DELIVERY_MAN("ROLE_HUB_DELIVERY_MAN"),
    COMPANY_DELIVERY_MAN("ROLE_COMPANY_DELIVERY_MAN");

    private final String role;
}
