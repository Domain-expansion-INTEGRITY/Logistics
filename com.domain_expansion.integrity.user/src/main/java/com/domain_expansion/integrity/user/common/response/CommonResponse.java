package com.domain_expansion.integrity.user.common.response;

import lombok.NonNull;

public interface CommonResponse {

    boolean success();

    @NonNull
    String message();
}
