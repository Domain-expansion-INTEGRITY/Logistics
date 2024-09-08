package com.domain_expansion.integrity.user.presentation.response;

import static lombok.AccessLevel.PRIVATE;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.model.UserRole;
import lombok.Builder;

@Builder(access = PRIVATE)
public record UserResponseDto(
    String username,
    UserRole role,
    String phoneNumber,
    String slackId
) {

    public static UserResponseDto from(User user) {

        return UserResponseDto.builder()
            .username(user.getUsername())
            .role(user.getRole())
            .phoneNumber(user.getPhoneNumber())
            .slackId(user.getSlackId())
            .build();

    }

}
