package com.domain_expansion.integrity.product.domain.model.info;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;

@Embeddable
@Getter
public class CompanyInfo {

    @Column(name = "company_id")
    private String companyId;

    @Transient
    private String companyName;

    public CompanyInfo(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public CompanyInfo() {
    }
}
