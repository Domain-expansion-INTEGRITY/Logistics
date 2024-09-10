package com.domain_expansion.integrity.auth.application;

import com.domain_expansion.integrity.auth.application.client.UserClient;
import com.domain_expansion.integrity.auth.application.client.request.UserLoginRequestDto;
import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.domain.UserAuthDto;
import com.domain_expansion.integrity.auth.domain.redis.RedisDomainService;
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
    public String checkLoginOfUser(UserLoginRequestDto requestDto) {

        UserResponseDto userInfo = userClient.loginUser(requestDto);

        //user auth 정보 redis에 저장
        redisDomainService.setUserData(UserAuthDto.from(userInfo));

        return jwtUtils.createToken(userInfo.username(), userInfo.role());
    }
}
