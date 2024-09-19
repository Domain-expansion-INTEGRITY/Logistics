package com.domain_expansion.integrity.user.presentation.controller;

import static com.domain_expansion.integrity.user.common.message.SuccessMessage.SUCCESS_CREATE_USER;
import static com.domain_expansion.integrity.user.common.message.SuccessMessage.SUCCESS_DELETE_USER;
import static com.domain_expansion.integrity.user.common.message.SuccessMessage.SUCCESS_FIND_SINGLE_USER;
import static com.domain_expansion.integrity.user.common.message.SuccessMessage.SUCCESS_FIND_USER_LIST;
import static com.domain_expansion.integrity.user.common.message.SuccessMessage.SUCCESS_UPDATE_USER;

import com.domain_expansion.integrity.user.application.UserService;
import com.domain_expansion.integrity.user.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.user.common.response.CommonResponse;
import com.domain_expansion.integrity.user.common.response.SuccessResponse;
import com.domain_expansion.integrity.user.common.security.IsMasterOrMe;
import com.domain_expansion.integrity.user.domain.model.UserRole;
import com.domain_expansion.integrity.user.domain.model.UserRole.Authority;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import com.domain_expansion.integrity.user.presentation.request.UserUpdateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserIdResponseDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("sign-up")
    public ResponseEntity<? extends CommonResponse> signUpUser(
        @Validated @RequestBody UserCreateRequestDto requestDto
    ) {
        // 여기서는 마스터 금지
        if (requestDto.role().equals(UserRole.MASTER)) {
//            throw new UserException(ExceptionMessage.INVALID_INPUT);
        }
        Long userId = userService.createUser(requestDto);

        return ResponseEntity.status(SUCCESS_CREATE_USER.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_CREATE_USER.getMessage(), userId));
    }


    /**
     * 유저 생성
     */
    @Secured({Authority.MASTER})
    @PostMapping
    public ResponseEntity<? extends CommonResponse> createUser(
        @Validated @RequestBody UserCreateRequestDto requestDto
    ) {
        Long userId = userService.createUser(requestDto);

        return ResponseEntity.status(SUCCESS_CREATE_USER.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_CREATE_USER.getMessage(), userId));
    }

    /**
     * 유저 단일 조회
     */
    @IsMasterOrMe
    @GetMapping("/{id}")
    public ResponseEntity<? extends CommonResponse> findUserById(
        @PathVariable("id") Long userId
    ) {
        UserResponseDto output = userService.findUserById(userId);

        return ResponseEntity.status(SUCCESS_FIND_SINGLE_USER.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_FIND_SINGLE_USER.getMessage(), output));
    }

    /**
     * 유저 목록 조회
     */
    @Secured({Authority.MASTER})
    @DefaultPageSize
    @GetMapping
    public ResponseEntity<? extends CommonResponse> findUserList(
        @PageableDefault Pageable pageable,
        @ModelAttribute
        UserSearchCondition userSearchCondition
    ) {
        //TODO: 관리자만 조회
        Page<UserResponseDto> userList = userService.findUserList(pageable, userSearchCondition);

        return ResponseEntity.status(SUCCESS_FIND_USER_LIST.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_FIND_USER_LIST.getMessage(), userList));
    }

    /**
     * 유저 업데이트
     */
    @Secured({Authority.MASTER})
    @PatchMapping("/{id}")
    public ResponseEntity<? extends CommonResponse> updateUserById(
        HttpServletRequest request,
        @PathVariable("id") Long id,
        @RequestBody UserUpdateRequestDto requestDto
    ) {
        UserResponseDto responseDto = userService.updateUserById(id, requestDto);

        return ResponseEntity.status(SUCCESS_UPDATE_USER.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_UPDATE_USER.getMessage(), responseDto));
    }

    /**
     * 비밀번호 변경 요청
     */
//    @PostMapping("/{id}}/password/otp-request")
//    public ResponseEntity<? extends CommonResponse> requestChangePassword(
//        HttpServletRequest request,
//        @PathVariable("id") Long id
//    ) {
//        String isSuccess = userService.requestPasswordOtp(id);
//
//        return ResponseEntity.status(SUCCESS_UPDATE_USER.getHttpStatus())
//            .body(SuccessResponse.of(SUCCESS_UPDATE_USER.getMessage(), isSuccess));
//    }

    /**
     * 비밀 번호와 랜덤 코드 입력
     */
//    @PatchMapping("/{id}/reset-password")
//    public ResponseEntity<? extends CommonResponse> changePassword(
//        HttpServletRequest request,
//        @PathVariable("id") Long id,
//        @RequestBody UserResetPasswordRequestDto requestDto
//    ) {
//        UserResponseDto responseDto = userService.updateUserPassword(id, requestDto);
//
//        return ResponseEntity.status(SUCCESS_UPDATE_USER.getHttpStatus())
//            .body(SuccessResponse.of(SUCCESS_UPDATE_USER.getMessage(), responseDto));
//    }

    /**
     * 유저 삭제
     */
    @Secured({Authority.MASTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<? extends CommonResponse> deleteUserById(
        @PathVariable("id") Long id
    ) {
        userService.deleteUserById(id);

        return ResponseEntity.status(SUCCESS_DELETE_USER.getHttpStatus())
            .body(SuccessResponse.of(SUCCESS_DELETE_USER.getMessage(),
                new UserIdResponseDto(id)));
    }

}
