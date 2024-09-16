package com.domain_expansion.integrity.delivery.application.client.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class CommonResponseData {

    private final Boolean success;
    private final String message;

}
