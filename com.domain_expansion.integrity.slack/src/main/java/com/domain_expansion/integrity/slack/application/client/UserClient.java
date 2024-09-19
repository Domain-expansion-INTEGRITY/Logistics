package com.domain_expansion.integrity.slack.application.client;

import com.domain_expansion.integrity.slack.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.slack.common.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping(value = "/users/{id}")
    SuccessResponse<UserResponseDto> findUserById(
        @PathVariable("id") Long id
    );

}
