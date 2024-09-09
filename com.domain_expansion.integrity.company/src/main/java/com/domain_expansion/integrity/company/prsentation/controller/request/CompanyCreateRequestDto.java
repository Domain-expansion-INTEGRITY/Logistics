package com.domain_expansion.integrity.company.prsentation.controller.request;

import com.domain_expansion.integrity.company.domain.model.CompanyType;
import jakarta.validation.constraints.NotBlank;

public record CompanyCreateRequestDto (
        @NotBlank
        Long userId,
        @NotBlank
        String hubId,
        @NotBlank
        String name,
        @NotBlank
        CompanyType companyType,
        @NotBlank
        String address
){

}
