package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.domain.model.User;
import java.util.List;

public interface UserDomainService {

    List<User> checkPhoneNumber(Long userId, String phoneNumber,
        String slackId);

}
