package com.domain_expansion.integrity.product.application.client.response;

import lombok.Getter;

@Getter
public class ValidateUserResponseData extends CommonResponseData {

    private final ValidateUserData data;

    public ValidateUserResponseData(Boolean success, String message, ValidateUserData data) {
        super(success, message);
        this.data = data;
    }
}
