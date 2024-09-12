package com.domain_expansion.integrity.auth.presentation.controller;

import com.domain_expansion.integrity.auth.application.AuthService;
import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.common.message.SuccessMessage;
import com.domain_expansion.integrity.auth.common.response.SuccessResponse;
import com.domain_expansion.integrity.auth.presentation.request.AuthLoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SuccessResponse<String>> signInUser(
        @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        // user 모듈에서 user 정보 갖고 온다.
        String jwtToken = authService.checkLoginOfUser(
            requestDto);

        response.addHeader(JwtUtils.AUTHORIZATION_HEADER, jwtToken);

        return ResponseEntity.status(SuccessMessage.SUCCESS_LOGIN_USER.getHttpStatus())
            .body(SuccessResponse.of(SuccessMessage.SUCCESS_LOGIN_USER.getMessage(), jwtToken));
    }

}
