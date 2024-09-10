package com.domain_expansion.integrity.order.application.client.response;

import lombok.Getter;

@Getter
public abstract class CommonResponseData {

    private final boolean success;

    private final String message;

    public CommonResponseData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
