package com.domain_expansion.integrity.hub.presentation.request.hub;

public record HubUpdateRequestDto(
    String name,
    String address,
    Double latitude,
    Double longitude,
    Long userId
) {

}
