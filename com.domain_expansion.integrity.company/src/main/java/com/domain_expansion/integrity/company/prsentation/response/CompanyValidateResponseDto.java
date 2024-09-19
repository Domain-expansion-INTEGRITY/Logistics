package com.domain_expansion.integrity.company.prsentation.response;

import com.domain_expansion.integrity.company.domain.model.Company;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CompanyValidateResponseDto(
        boolean isOwner
) {
    public static CompanyValidateResponseDto of(boolean result) {
        return CompanyValidateResponseDto.builder().isOwner(result).build();
    }
}
