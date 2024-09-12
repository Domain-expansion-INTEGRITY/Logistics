package com.domain_expansion.integrity.company.application.client.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
public record HubResponseDto(
        String hubId,
        Long userId,
        String name,
        String address,
        double latitude,
        double longitude
) {

}
