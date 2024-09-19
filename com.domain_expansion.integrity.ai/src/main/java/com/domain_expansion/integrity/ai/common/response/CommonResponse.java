package com.domain_expansion.integrity.ai.common.response;

import lombok.NonNull;

public interface CommonResponse {

    boolean success();

    @NonNull
    String message();


}
