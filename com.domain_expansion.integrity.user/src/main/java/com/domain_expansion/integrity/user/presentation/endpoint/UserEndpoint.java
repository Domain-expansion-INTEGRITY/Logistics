package com.domain_expansion.integrity.user.presentation.endpoint;

import com.domain_expansion.integrity.user.application.UserService;
import com.domain_expansion.integrity.user.common.response.SuccessResponse;
import com.domain_expansion.integrity.user.presentation.request.UserLoginRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MSa 내부용 EndPoint
 */
@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserEndpoint {

    private final UserService userService;

    /*
     * 로그인 조회 (auth Server에서의 요청)
     * 추후 배포 시 Gateway 외부에서의 접근 제어를 할 수 있을 거라 기대
     * TODO: Queue 통신으로 해결할 수 있을까
     */
    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse<UserResponseDto>> signInUser(
        @RequestBody UserLoginRequestDto requestDto
    ) {

        UserResponseDto responseDto = userService.findUserByUsernameAndPassword(
            requestDto);

        return ResponseEntity.status(HttpStatus.OK)
            .body(SuccessResponse.of("유저 정보 전달", responseDto));
    }

}
