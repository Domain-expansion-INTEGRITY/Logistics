package com.domain_expansion.integrity.product.application.client.response;

import lombok.Getter;

@Getter
public class CompanyResponseData extends CommonResponseData {

    private final CompanyResponseDto data;

    public CompanyResponseData(Boolean success, String message, CompanyResponseDto data) {
        super(success, message);
        this.data = data;
    }
}
