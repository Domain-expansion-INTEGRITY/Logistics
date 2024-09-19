package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.presentation.request.UserLoginRequestDto;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReadService {

    public UserResponseDto findUserById(Long userId);

    public Page<UserResponseDto> findUserList(Pageable pageable,
        UserSearchCondition searchCondition);

    public UserResponseDto findUserByUsernameAndPassword(UserLoginRequestDto requestDto);

}
