package com.domain_expansion.integrity.hub.infrastructure.repository;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.QDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.QHub;
import com.domain_expansion.integrity.hub.domain.model.QHubDeliveryMan;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubDeliverManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .leftJoin(hub,qHubDeliveryMan.hub)
                .leftJoin(qHubDeliveryMan.hubDeliveryMan)
                .fetchJoin()
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


}
