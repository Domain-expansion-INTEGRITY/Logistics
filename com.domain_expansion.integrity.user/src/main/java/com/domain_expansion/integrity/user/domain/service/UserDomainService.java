package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.domain.model.User;

public interface UserDomainService {

    Long saveUser(User user);

    User findUserById(Long userId);
}
