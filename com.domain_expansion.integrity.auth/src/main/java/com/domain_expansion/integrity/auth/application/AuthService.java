package com.domain_expansion.integrity.auth.application;

import com.domain_expansion.integrity.auth.application.client.request.UserLoginRequestDto;

public interface AuthService {


    String checkLoginOfUser(UserLoginRequestDto requestDto);
}
