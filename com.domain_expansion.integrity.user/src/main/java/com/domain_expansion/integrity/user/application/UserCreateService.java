package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;

public interface UserCreateService {

    public Long createUser(UserCreateRequestDto requestDto);

}
