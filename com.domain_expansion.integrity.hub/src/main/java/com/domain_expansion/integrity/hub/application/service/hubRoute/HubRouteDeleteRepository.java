package com.domain_expansion.integrity.hub.application.service.hubRoute;


import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;

public interface HubRouteDeleteRepository {

    void deleteHubRoute(String hubRouteId, UserDetailsImpl userDetails);
}
