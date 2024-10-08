package com.domain_expansion.integrity.auth.application.client;

import com.domain_expansion.integrity.auth.application.client.request.UserLoginRequestDto;
import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.auth.common.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping(value = "/users/sign-in")
    SuccessResponse<UserResponseDto> loginUser(
        @RequestBody UserLoginRequestDto requestDto
    );
}
