package com.domain_expansion.integrity.user.presentation.request;

import com.domain_expansion.integrity.user.domain.model.UserRole;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDto(

    // username 형식
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "잘못된 Username 입력입니다.")
    String username,
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$"
        , message = "잘못된 비밀번호 입력입니다.")
    String password,
    UserRole role,
    String phoneNumber,
    String slackId

) {

}
