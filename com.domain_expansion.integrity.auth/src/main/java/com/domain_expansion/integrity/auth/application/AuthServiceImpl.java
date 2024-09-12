package com.domain_expansion.integrity.auth.application;

import com.domain_expansion.integrity.auth.application.client.UserClient;
import com.domain_expansion.integrity.auth.application.client.request.UserLoginRequestDto;
import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.common.response.SuccessResponse;
import com.domain_expansion.integrity.auth.domain.dto.UserAuthDto;
import com.domain_expansion.integrity.auth.domain.redis.RedisDomainService;
import com.domain_expansion.integrity.auth.presentation.request.AuthLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final RedisDomainService redisDomainService;
    private final JwtUtils jwtUtils;

    /**
     * 로그인 처리 및 Jwt Token 생성
     */
    @Override
    public String checkLoginOfUser(AuthLoginRequestDto requestDto) {

        // User 로그인에 맞춰서 변환 후 요청을 보낸다
        SuccessResponse<UserResponseDto> userOutput = userClient.loginUser(
            UserLoginRequestDto.from(requestDto.username(), requestDto.password()));

        UserResponseDto userInfo = userOutput.data();

        //user auth 정보 redis에 저장
        redisDomainService.setUserData(UserAuthDto.from(userInfo));

        return jwtUtils.createToken(userInfo.userId(), userInfo.role());
    }
}
