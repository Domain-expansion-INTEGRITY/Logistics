package com.domain_expansion.integrity.company.prsentation.response;

import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CompanyResponseDto(
        String companyId,
        Long userId,
        String hubId,
        String name,
        CompanyType companyType,
        String address
) {

    public static CompanyResponseDto from(Company company) {
        return CompanyResponseDto.builder()
                .companyId(company.getCompanyId())
                .userId(company.getUserId())
                .companyType(company.getCompanyType())
                .address(company.getCompanyAddress())
                .name(company.getName())
                .hubId(company.getHubId()).build();
    }
}
