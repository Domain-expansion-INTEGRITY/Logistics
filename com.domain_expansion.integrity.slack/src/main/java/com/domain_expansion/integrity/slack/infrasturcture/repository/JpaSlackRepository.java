package com.domain_expansion.integrity.slack.infrasturcture.repository;

import com.domain_expansion.integrity.slack.domain.model.Slack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSlackRepository extends JpaRepository<Slack, String> {

}
