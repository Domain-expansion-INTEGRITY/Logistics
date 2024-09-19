package com.domain_expansion.integrity.hub.domain.repository;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface HubRepository{

    Optional<Hub> findById(String id);

    Optional<Hub> findHubByUserId(Long userId);

    Hub save(Hub hub);

    Optional<Hub> findByHubIdAndUserId(String hubId, Long userId);

    Optional<Hub> findByStartRoutes_HubRouteId(String hubRouteId);

    @Query(value = "WITH RECURSIVE HubPath AS (" +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, hr.distance as total_distance, hr.duration as total_duration " +
            "FROM p_hub_route hr " +
            "WHERE " +
            "hr.start_hub_id = :startHubId " +
            "UNION ALL " +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, total_distance + hr.distance , total_duration + hr.duration " +
            "FROM p_hub_route hr " +
            "INNER JOIN HubPath hp ON hr.start_hub_id = hp.end_hub_id" +
            ") " +
            "SELECT total_duration,total_distance " +
            "FROM HubPath " +
            "WHERE 1=1 " +
            "AND end_hub_id = :endHubId ",
            nativeQuery = true)
    List<Object[]> findRouteInfoForward(
            @Param("startHubId") String startHubId,
            @Param("endHubId") String endHubId);

    @Query(value = "WITH RECURSIVE HubPath AS (" +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, hr.distance as total_distance, hr.duration as total_duration " +
            "FROM p_hub_route hr " +
            "WHERE " +
            "hr.end_hub_id = :startHubId " +
            "UNION ALL " +
            "SELECT hr.start_hub_id, hr.end_hub_id, hr.duration, hr.distance, total_distance + hr.distance , total_duration + hr.duration " +
            "FROM p_hub_route hr " +
            "INNER JOIN HubPath hp ON " +
            " hr.end_hub_id = hp.start_hub_id " +
            ") " +
            "SELECT total_duration,total_distance " +
            "FROM HubPath " +
            "WHERE 1=1 " +
            "AND start_hub_id = :endHubId ",
            nativeQuery = true)
    List<Object[]> findRouteInfoBackWard(
            @Param("startHubId") String startHubId,
            @Param("endHubId") String endHubId);

}
