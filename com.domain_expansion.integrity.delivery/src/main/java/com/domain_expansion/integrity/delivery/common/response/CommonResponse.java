package com.domain_expansion.integrity.delivery.common.response;

import lombok.NonNull;

public interface CommonResponse {

    boolean success();

    @NonNull
    String message();
}
