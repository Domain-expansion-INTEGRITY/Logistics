package com.domain_expansion.integrity.product.application.client.response;

public record CompanyResponseDto (
        String companyId,
        String userId,
        String hubId,
        String name,
        String type,
        String address
) {


}
