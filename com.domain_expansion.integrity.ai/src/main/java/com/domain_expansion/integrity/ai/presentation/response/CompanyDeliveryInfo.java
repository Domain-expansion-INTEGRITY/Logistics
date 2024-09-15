package com.domain_expansion.integrity.ai.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

//출발지, 도착지, 위도, 경도
public record CompanyDeliveryInfo(
    @JsonProperty("starting")
    String starting,
    @JsonProperty("ending")
    String ending,
    @JsonProperty("latitude")
    Double latitude,
    @JsonProperty("longitude")
    Double longitude
) {

}
