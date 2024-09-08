package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;

public interface UserService {

    public Long createUser(UserCreateRequestDto requestDto);

    public UserResponseDto findUserById(Long userId);

}
