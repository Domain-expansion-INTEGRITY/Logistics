package com.domain_expansion.integrity.hub.domain.repository;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import java.util.Optional;

public interface HubRepository{

    Optional<Hub> findById(String id);

    Hub save(Hub hub);

    Optional<Hub> findByHubIdAndUserId(String hubId, Long userId);

    //List<Hub> saveAll(Collection<Hub> hubs);

    Optional<Hub> findByStartRoutes_HubRouteId(String hubRouteId);

}
