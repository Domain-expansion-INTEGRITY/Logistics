package com.domain_expansion.integrity.company.prsentation.request;

import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import jakarta.validation.constraints.NotBlank;

public record CompanyUpdateRequestDto(
        @NotBlank
        String hubId,
        String name,
        CompanyType companyType,
        String address
) {

}
