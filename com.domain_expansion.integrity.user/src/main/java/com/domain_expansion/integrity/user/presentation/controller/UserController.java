package com.domain_expansion.integrity.user.presentation.controller;

import com.domain_expansion.integrity.user.application.UserService;
import com.domain_expansion.integrity.user.common.message.SuccessMessage;
import com.domain_expansion.integrity.user.common.response.SuccessResponse;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserIdResponseDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public SuccessResponse<UserIdResponseDto> createUser(
        @Validated @RequestBody UserCreateRequestDto requestDto
    ) {
        //
        Long userId = userService.createUser(requestDto);

        return SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_USER.getMessage(),
            new UserIdResponseDto(userId));
    }

    @GetMapping("/{id}")
    public SuccessResponse<UserResponseDto> findUserById(
        @PathVariable("id") Long userId
    ) {

        UserResponseDto output = userService.findUserById(userId);

        return SuccessResponse.of("유저 조회가 되었습니다.", output);
    }

}
