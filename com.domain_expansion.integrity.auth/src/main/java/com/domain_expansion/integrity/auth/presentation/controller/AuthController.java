package com.domain_expansion.integrity.auth.presentation.controller;

import com.domain_expansion.integrity.auth.application.AuthService;
import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.common.message.SuccessMessage;
import com.domain_expansion.integrity.auth.common.response.SuccessResponse;
import com.domain_expansion.integrity.auth.presentation.request.AuthLoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public SuccessResponse<String> signInUser(
        @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        // user 모듈에서 user 정보 갖고 온다.
        String jwtToken = authService.checkLoginOfUser(
            requestDto);

        response.addHeader(JwtUtils.AUTHORIZATION_HEADER, jwtToken);

        // TODO: 테스트를 위해서 잠깐 bearer 안붙임
        return SuccessResponse.of(SuccessMessage.SUCCESS_LOGIN_USER.getMessage(),
            jwtToken.substring(7));
    }

}
