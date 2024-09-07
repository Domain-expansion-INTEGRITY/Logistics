package com.domain_expansion.integrity.product.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductCreateRequestDto(
        @NotBlank
        String companyId,

        @NotBlank
        String productName,

        @Positive(message = "재고는 음수가 될 수 없습니다.")
        Integer stock
) {

}
