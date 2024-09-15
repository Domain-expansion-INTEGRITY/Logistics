package com.domain_expansion.integrity.hub.infrastructure.repository;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import com.domain_expansion.integrity.hub.domain.model.QDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.QHub;
import com.domain_expansion.integrity.hub.domain.model.QHubDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.QHubRoute;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubDeliverManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubQueryRepositoryImpl implements HubQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<HubResponseDto> searchHubs(HubSearchCondition condition, Pageable pageable) {
        QHub hub = QHub.hub;
        QDeliveryMan deliveryMan = QDeliveryMan.deliveryMan;
        QHubDeliveryMan qHubDeliveryMan = QHubDeliveryMan.hubDeliveryMan;

        BooleanBuilder builder = new BooleanBuilder();

        if(condition.name() != null)
        {
            builder.and(hub.name.containsIgnoreCase(condition.name()));
        }

        if(condition.address() != null)
        {
            builder.and(hub.address.containsIgnoreCase(condition.address()));
        }

        builder.and(hub.isDelete.eq(false));

        JPAQuery<Hub> query = queryFactory.selectFrom(hub)
                .leftJoin(hub.deliveryMans, qHubDeliveryMan)
                .leftJoin(qHubDeliveryMan.deliveryMan, deliveryMan)
                .where(builder)
                .orderBy(hub.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long total = query.fetchCount();

        List<HubResponseDto> hubList = query.stream().map(
                hubData -> new HubResponseDto(
                        hubData.getHubId(),
                        hubData.getUserId(),
                        hubData.getName(),
                        hubData.getAddress(),
                        hubData.getHubLatitude().getLatitude(),
                        hubData.getHubLongitude().getLongitude(),
                        hubData.getDeliveryMans().stream().map(
                                delivery ->
                                new HubDeliverManResponseDto(
                                        delivery.getHubDeliveryManId(),
                                        delivery.getDeliveryMan().getUserId()
                                )
                        ).toList()
                )
                )
                .toList();

        return new PageImpl<>(hubList, pageable, total);
    }

    @Override
    public Page<HubRouteResponseDto> searchHubRoutes(HubRouteSearchCondition condition, Pageable pageable) {

        QHubRoute hubRoute = QHubRoute.hubRoute;
        QHub startHub = QHub.hub;

        BooleanBuilder builder = new BooleanBuilder();

        if(condition.distance() != null)
        {
            builder.and(hubRoute.distance.eq(condition.distance()));
        }

        if(condition.StartHubId() != null)
        {
            builder.and(hubRoute.startHub.hubId.eq(condition.StartHubId()));
        }

        builder.and(hubRoute.isDelete.eq(false));

        JPAQuery<HubRoute> query = queryFactory.select(hubRoute)
                .from(hubRoute)
                .where(builder)
                .orderBy(hubRoute.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Long total = query.fetchCount();

        List<HubRouteResponseDto> lists = query.stream().map(HubRouteResponseDto::of).toList();

        return new PageImpl<>(lists, pageable, total);
    }

}
