package com.domain_expansion.integrity.order.presentation.request;

public record OrderSearchCondition(
        String sellerCompanyId,
        String buyerCompanyId,
        String productId
) {

}