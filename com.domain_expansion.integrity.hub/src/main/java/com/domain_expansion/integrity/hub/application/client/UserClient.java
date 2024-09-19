package com.domain_expansion.integrity.hub.application.client;

import com.domain_expansion.integrity.hub.application.client.config.FeignConfig;
import com.domain_expansion.integrity.hub.application.client.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {
    
    @GetMapping("/users/{userId}")
    UserResponseDto findByUserId(@PathVariable("userId") Long userId);
}
