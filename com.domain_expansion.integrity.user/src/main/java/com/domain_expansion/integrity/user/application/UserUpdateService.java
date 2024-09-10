package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;

public interface UserUpdateService {

    public UserResponseDto updateUserById(Long userId);

}
