package com.domain_expansion.integrity.auth.application;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.domain_expansion.integrity.auth.application.client.UserClient;
import com.domain_expansion.integrity.auth.application.client.request.UserLoginRequestDto;
import com.domain_expansion.integrity.auth.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.auth.common.jwt.JwtUtils;
import com.domain_expansion.integrity.auth.common.response.SuccessResponse;
import com.domain_expansion.integrity.auth.domain.UserRole;
import com.domain_expansion.integrity.auth.domain.redis.RedisDomainService;
import com.domain_expansion.integrity.auth.presentation.request.AuthLoginRequestDto;
import feign.FeignException.NotFound;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private final String TEST_JWT_KEY = "7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF";
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserClient userClient;

    @Mock
    private RedisDomainService redisDomainService;

    @Spy
    private JwtUtils jwtUtils = new JwtUtils(TEST_JWT_KEY);


    @Nested
    @DisplayName("유저 인증 테스트 모음")
    class authRequest {

        @Test
        @DisplayName("로그인 성공 - 유저 로그인 성공")
        void 로그인_성공_테스트() throws NoSuchFieldException, IllegalAccessException {

            // given
            AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto("username",
                "password");

            UserLoginRequestDto requestDto = new UserLoginRequestDto(
                authLoginRequestDto.username(), authLoginRequestDto.password());

            UserResponseDto userResponseMock = mock(UserResponseDto.class);

            when(userResponseMock.userId()).thenReturn(1L);
            when(userResponseMock.role()).thenReturn(UserRole.HUB_COMPANY);
            when(userResponseMock.username()).thenReturn(authLoginRequestDto.username());

            when(userClient.loginUser(requestDto)).thenReturn(
                SuccessResponse.of("", userResponseMock));

            // when
            String token = authService.checkLoginOfUser(authLoginRequestDto);

            // then
            Assertions.assertThat(token).startsWith(JwtUtils.BEARER_PREFIX);
        }

        @Test
        @DisplayName("로그인 실패 - 잘못된 유저 비밀번호 가정")
        void 로그인_실패_테스트() {
            // given
            AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto("username",
                "password");

            UserLoginRequestDto requestDto = new UserLoginRequestDto(
                authLoginRequestDto.username(), authLoginRequestDto.password());
            // when
            NotFound feignException = mock(NotFound.class);
            given(userClient.loginUser(requestDto)).willThrow(feignException);

            // then
            Assertions.assertThatThrownBy(
                () -> authService.checkLoginOfUser(authLoginRequestDto)
            ).isInstanceOf(NotFound.class);

        }


    }

}