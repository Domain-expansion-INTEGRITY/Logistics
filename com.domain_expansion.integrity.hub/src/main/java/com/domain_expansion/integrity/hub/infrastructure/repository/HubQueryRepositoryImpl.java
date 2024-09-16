package com.domain_expansion.integrity.hub.infrastructure.repository;

import com.domain_expansion.integrity.hub.application.shared.RoleConstants;
import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import com.domain_expansion.integrity.hub.domain.model.QDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.QHub;
import com.domain_expansion.integrity.hub.domain.model.QHubDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.QHubRoute;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubDeliverManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.Projection;
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

    @Override
    public Page<DeliveryManResponseDto> searchDeliveryMans(DeliveryManSearchCondition condition,
            Pageable pageable, UserDetailsImpl userDetail) {

        QDeliveryMan qDeliveryMan = QDeliveryMan.deliveryMan;
        QHub qHub = QHub.hub;
        QHubDeliveryMan qHubDeliveryMan = QHubDeliveryMan.hubDeliveryMan;

        BooleanBuilder builder = new BooleanBuilder();

        if(condition.deliveryManId() != null)
        {
            builder.and(qDeliveryMan.deliveryManId.eq(condition.deliveryManId()));
        }

        if(condition.userId() != null)
        {
            builder.and(qDeliveryMan.userId.eq(condition.userId()));
        }

        if(condition.hubName() != null)
        {
            builder.and(qHub.name.like("%" + condition.hubName() + "%"));
        }

        builder.and(qDeliveryMan.isDelete.eq(false));

        Long total = null;
        //자신의 허브에 소속된 배송담당자만 검색
        if(RoleConstants.ROLE_HUB_MANAGER.equals(userDetail.getRole()))
        {

            builder.and(qHubDeliveryMan.hub.userId.eq(userDetail.getUserId()));

            JPAQuery<DeliveryManResponseDto> query = queryFactory.select(
                            Projections.constructor(DeliveryManResponseDto.class,
                                    qDeliveryMan.deliveryManId,
                                    qDeliveryMan.userId,
                                    qHubDeliveryMan.hub.hubId
                            )
                    )
                    .from(qHubDeliveryMan)
                    .leftJoin(qHubDeliveryMan.deliveryMan,qDeliveryMan)
                    .leftJoin(qHubDeliveryMan.hub, qHub)
                    .where(builder)
                    .orderBy(qHubDeliveryMan.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());

            total = query.fetchCount();

            List<DeliveryManResponseDto> content = query.fetch();

            return new PageImpl<>(content, pageable, total);
        }else{

            JPAQuery<DeliveryManResponseDto> query = queryFactory.select(
                            Projections.constructor(DeliveryManResponseDto.class,
                                    qDeliveryMan.deliveryManId,
                                    qDeliveryMan.userId,
                                    qHubDeliveryMan.hub.hubId
                                    )
                    )
                    .from(qDeliveryMan)
                    .leftJoin(qHubDeliveryMan).on(qDeliveryMan.deliveryManId.eq(qHubDeliveryMan.deliveryMan.deliveryManId))
                    .leftJoin(qHubDeliveryMan.hub, qHub)
                    .where(builder)
                    .orderBy(qDeliveryMan.createdAt.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());

            total = query.fetchCount();

            List<DeliveryManResponseDto> content = query.fetch();

            return new PageImpl<>(content, pageable, total);
        }
    }

}
