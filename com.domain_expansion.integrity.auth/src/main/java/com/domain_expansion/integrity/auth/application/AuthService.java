package com.domain_expansion.integrity.auth.application;

import com.domain_expansion.integrity.auth.presentation.request.AuthLoginRequestDto;

public interface AuthService {


    String checkLoginOfUser(AuthLoginRequestDto requestDto);
}
