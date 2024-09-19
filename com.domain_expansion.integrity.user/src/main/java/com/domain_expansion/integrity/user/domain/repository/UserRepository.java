package com.domain_expansion.integrity.user.domain.repository;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.infrastructure.repository.JpaUserRepository;
import java.util.Optional;

public interface UserRepository extends JpaUserRepository {

    Optional<User> findByUsername(String username);

}
