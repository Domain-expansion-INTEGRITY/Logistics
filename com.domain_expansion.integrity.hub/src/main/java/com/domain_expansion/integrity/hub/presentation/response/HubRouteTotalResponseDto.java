package com.domain_expansion.integrity.hub.presentation.response;

public record HubRouteTotalResponseDto (
        String startHubId,
        String endHubId,
        Integer total_duration,
        Integer total_distance
){

}
