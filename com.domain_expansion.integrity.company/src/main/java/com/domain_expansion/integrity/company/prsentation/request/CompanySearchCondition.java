package com.domain_expansion.integrity.company.prsentation.request;

import com.domain_expansion.integrity.company.domain.model.CompanyType;

public record CompanySearchCondition(
        String companyName,
        CompanyType companyType
) {

}
