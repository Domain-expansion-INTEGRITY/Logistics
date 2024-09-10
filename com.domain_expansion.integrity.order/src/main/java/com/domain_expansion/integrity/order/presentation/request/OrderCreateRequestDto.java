package com.domain_expansion.integrity.order.presentation.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record OrderCreateRequestDto(
        @NotBlank
        String sellerCompanyId,

        @NotBlank
        String buyerCompanyId,

        List<OrderProductRequestDto> productList,

        String address,

        String receiver,

        String receiverSlackId
) {

}
