package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(User user) {

        User userInfo = userRepository.save(user);

        return userInfo.getId();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }
}
