package com.domain_expansion.integrity.product.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProductUpdateRequestDto(
        @NotBlank(message = "상품명을 입력해주세요.")
        @Pattern(regexp = "[a-zA-Z가-힣-_]", message = "상품명은 한글 및 영어, 하이픈과 언더바('-', '_')만 입력 할 수 있습니다.")
        String productName
) {

}
