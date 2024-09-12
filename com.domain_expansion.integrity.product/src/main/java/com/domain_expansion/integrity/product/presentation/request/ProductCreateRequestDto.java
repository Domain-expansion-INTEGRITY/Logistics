package com.domain_expansion.integrity.product.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductCreateRequestDto(
        @NotBlank
        String companyId,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z가-힣-_]+$", message = "상품명은 한글 및 영어, 하이픈과 언더바('-', '_')만 입력 할 수 있습니다.")
        String productName,

        @Positive(message = "재고는 음수가 될 수 없습니다.")
        Integer stock
) {

}
