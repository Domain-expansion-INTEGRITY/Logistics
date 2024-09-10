package com.domain_expansion.integrity.company.domain.model;

import com.domain_expansion.integrity.company.common.entity.BaseEntity;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company")
public class Company extends BaseEntity {

    @Id
    private String companyId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "hub_id")
    private String hubId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyType companyType;

    @Column(name = "address",nullable = false)
    private String companyAddress;

    @ColumnDefault(value = "false")
    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Builder(access = AccessLevel.PRIVATE)
    public Company(String companyId,Long userId, String hubId, String name, CompanyType companyType, String companyAddress) {
        this.companyId = companyId;
        this.userId = userId;
        this.hubId = hubId;
        this.name = name;
        this.companyType = companyType;
        this.companyAddress = companyAddress;
    }

    public static Company from(
            String companyId,Long userId, String hubId, String name, CompanyType companyType, String companyAddress
    )
    {
        return Company.builder()
                .companyId(companyId)
                .userId(userId)
                .hubId(hubId)
                .name(name)
                .companyType(companyType)
                .companyAddress(companyAddress)
                .build();
    }

    public void deleteCompany() {
        this.isDelete = true;
    }

    public void updateWith(CompanyUpdateRequestDto requestDto) {

        if( requestDto.hubId() != null)
        {
            this.hubId = requestDto.hubId();
        }

        if( requestDto.name() != null)
        {
            this.name = requestDto.name();
        }

        if( requestDto.companyType() != null)
        {
            this.companyType = requestDto.companyType();
        }

        if( requestDto.address() != null)
        {
            this.companyAddress = requestDto.address();
        }
    }
}
