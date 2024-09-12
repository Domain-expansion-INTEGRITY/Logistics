package com.domain_expansion.integrity.order.application.client.response;

import lombok.Getter;

@Getter
public class CompanyResponseData extends CommonResponseData {

    private final CompanyResponseDto data;

    private CompanyResponseData(boolean success, String message, CompanyResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public record CompanyResponseDto(
            String companyId,
            String userId,
            String hubId,
            String name,
            String type,
            String address
    ) {

    }
}
