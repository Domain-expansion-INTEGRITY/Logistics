package com.domain_expansion.integrity.hub.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
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
    @CreatedBy
    protected Long createdBy;

    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(name = "updated_by")
    @LastModifiedBy
    protected Long updatedBy;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    protected Long deletedBy;


    public void setDeletedValue(Long userId)
    {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userId;
    }
}