package com.domain_expansion.integrity.user.presentation.endpoint;

import com.domain_expansion.integrity.user.application.UserService;
import com.domain_expansion.integrity.user.presentation.request.UserLoginRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 로그인 조회 (auth Server에서의 요청)
     */
    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signInUser(
        @RequestBody UserLoginRequestDto requestDto
    ) {

        UserResponseDto responseDto = userService.findUserByUsernameAndPassword(
            requestDto);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 유저 단일 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
        @PathVariable("id") Long id
    ) {
        UserResponseDto responseDto = userService.findUserById(id);

        return ResponseEntity.ok(responseDto);
    }

}
