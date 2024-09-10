package com.domain_expansion.integrity.user.presentation.controller;

import com.domain_expansion.integrity.user.application.UserService;
import com.domain_expansion.integrity.user.common.message.SuccessMessage;
import com.domain_expansion.integrity.user.common.response.SuccessResponse;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserIdResponseDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    /**
     * 유저 생성
     */
    @PostMapping
    public SuccessResponse<UserIdResponseDto> createUser(
        @Validated @RequestBody UserCreateRequestDto requestDto
    ) {
        //
        Long userId = userService.createUser(requestDto);

        return SuccessResponse.of(SuccessMessage.SUCCESS_CREATE_USER.getMessage(),
            new UserIdResponseDto(userId));
    }

    /**
     * 유저 단일 조회
     */
    @GetMapping("/{id}")
    public SuccessResponse<UserResponseDto> findUserById(
        @PathVariable("id") Long userId
    ) {

        UserResponseDto output = userService.findUserById(userId);

        return SuccessResponse.of(SuccessMessage.SUCCESS_FIND_SINGLE_USER.getMessage(), output);
    }

    /**
     * 유저 목록 조회
     */
    @GetMapping
    public SuccessResponse<Page<UserResponseDto>> findUserList(
        @PageableDefault Pageable pageable
    ) {

        return SuccessResponse.of(SuccessMessage.SUCCESS_FIND_USER_LIST.getMessage(), null);
    }

    /**
     * 유저 업데이트
     */
    @PatchMapping("/{id}")
    public SuccessResponse<UserResponseDto> updateUserById(
        @PathVariable("id") Long id
    ) {

        return SuccessResponse.of(SuccessMessage.SUCCESS_UPDATE_USER.getMessage(), null);
    }

    /**
     * 유저 삭제
     */
    @DeleteMapping("/{id}")
    public SuccessResponse<UserIdResponseDto> deleteUserById(
        @PathVariable("id") Long id
    ) {

        return SuccessResponse.of(SuccessMessage.SUCCESS_DELETE_USER.getMessage(), null);
    }

}
