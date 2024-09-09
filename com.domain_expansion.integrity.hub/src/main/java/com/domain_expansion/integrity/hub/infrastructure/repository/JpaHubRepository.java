package com.domain_expansion.integrity.hub.infrastructure.repository;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHubRepository extends JpaRepository<Hub, String> {

}
