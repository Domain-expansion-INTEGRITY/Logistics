package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.domain.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserQueryRepository userQueryRepository;

    /**
     * 중복이 있는지 확인하는 함수들
     */
    @Override
    public Boolean checkPhoneNumber(Long userId, String phoneNumber, String slackId) {
        return userQueryRepository.checkExistFieldInfo(userId, phoneNumber, slackId);
    }
}
