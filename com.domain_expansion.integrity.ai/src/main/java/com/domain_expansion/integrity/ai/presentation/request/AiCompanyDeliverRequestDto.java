package com.domain_expansion.integrity.ai.presentation.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AiCompanyDeliverRequestDto(
    @NotNull
    String hubId,
    @NotNull(message = "위도는 필수입니다.")
    Double latitude,
    @NotNull(message = "경도는 필수입니다.")
    Double longitude,
    @NotNull(message = "허브 주소는 필수입니다.")
    String hubAddress,
    @Size(min = 1, message = "최소 하나의 배송지는 있어야 합니다.")
    List<DeliveryInfo> deliveryList,
    Long userId
) {

}
