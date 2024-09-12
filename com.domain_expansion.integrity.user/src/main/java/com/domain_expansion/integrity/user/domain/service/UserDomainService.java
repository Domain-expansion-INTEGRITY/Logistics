package com.domain_expansion.integrity.user.domain.service;

public interface UserDomainService {

    Long checkPhoneNumber(Long userId, String phoneNumber,
        String slackId);

}
