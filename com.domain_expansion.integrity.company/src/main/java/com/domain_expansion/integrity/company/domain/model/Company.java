package com.domain_expansion.integrity.company.domain.model;

import com.domain_expansion.integrity.company.common.entity.BaseEntity;
import com.domain_expansion.integrity.company.domain.model.vo.CompanyAddress;
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

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private CompanyType companyType;

    @Column(name = "address")
    @Embedded
    private CompanyAddress companyAddress;

    @ColumnDefault(value = "false")
    @Column(name = "is_delete")
    private Boolean isDelete;

    @Builder(access = AccessLevel.PROTECTED)
    public Company(Long userId, String hubId, String name, CompanyType companyType, CompanyAddress companyAddress) {
        this.userId = userId;
        this.hubId = hubId;
        this.name = name;
        this.companyType = companyType;
        this.companyAddress = companyAddress;
    }

}
