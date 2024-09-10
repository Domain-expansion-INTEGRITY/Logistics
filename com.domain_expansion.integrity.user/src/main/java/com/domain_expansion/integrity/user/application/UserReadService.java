package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.presentation.request.UserLoginRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;

public interface UserReadService {

    public UserResponseDto findUserById(Long userId);

    public UserResponseDto findUserByUsernameAndPassword(UserLoginRequestDto requestDto);

}
