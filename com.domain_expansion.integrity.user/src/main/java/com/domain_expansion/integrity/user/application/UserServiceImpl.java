package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.service.UserDomainService;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    // Domain
    private final UserDomainService userDomainService;
    // password encode
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(UserCreateRequestDto requestDto) {

        User user = User.from(requestDto, passwordEncoder.encode(requestDto.password()));

        return userDomainService.saveUser(user);
    }
    
    public UserResponseDto findUserById(Long userId) {
        User userInfo = userDomainService.findUserById(userId);
        return UserResponseDto.from(userInfo);
    }
}
