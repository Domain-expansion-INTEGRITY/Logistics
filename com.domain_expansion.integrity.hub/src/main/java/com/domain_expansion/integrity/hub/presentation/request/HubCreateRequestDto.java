package com.domain_expansion.integrity.hub.presentation.request;

public record HubCreateRequestDto(
        Long userId,
        String name,
        String address,
        double index,
        double latitude,
        double longitude
) {

}
