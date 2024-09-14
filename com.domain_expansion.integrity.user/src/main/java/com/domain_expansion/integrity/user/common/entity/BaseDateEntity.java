package com.domain_expansion.integrity.user.common.entity;

import com.domain_expansion.integrity.user.common.entity.auditor.JpaAuditingConfig;
import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDateEntity {

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    protected LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    protected Long createdUser;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    protected Long updatedUser;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    protected Long deletedUser;

    public void deleteEntity() {
        this.deletedAt = LocalDateTime.now();
        JpaAuditingConfig jpaAuditingConfig = new JpaAuditingConfig();
        this.deletedUser = jpaAuditingConfig.getCurrentAuditor()
            .orElseThrow(() -> new UserException(
                ExceptionMessage.AUTHORIZATION));
    }
}
