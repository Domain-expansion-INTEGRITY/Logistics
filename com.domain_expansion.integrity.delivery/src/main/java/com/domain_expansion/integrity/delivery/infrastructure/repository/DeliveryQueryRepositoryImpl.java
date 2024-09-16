package com.domain_expansion.integrity.delivery.infrastructure.repository;

import static com.domain_expansion.integrity.delivery.domain.model.QDelivery.*;
import static com.domain_expansion.integrity.delivery.domain.model.QDeliveryHistory.*;
import static com.domain_expansion.integrity.delivery.domain.model.QHubDeliveryHistory.*;

import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.model.QDeliveryHistory;
import com.domain_expansion.integrity.delivery.domain.model.QHubDeliveryHistory;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryQueryRepository;
import com.domain_expansion.integrity.delivery.presentation.request.DeliverySearchCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryQueryRepositoryImpl implements DeliveryQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public DeliveryQueryRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Page<Delivery> findAllByCondition(Pageable pageable,
            DeliverySearchCondition searchCondition) {

        List<Delivery> content = queryFactory.selectFrom(delivery)
                .where(deliveryManIdEq(searchCondition.deliveryManId()),
                        deliveryStatusEq(searchCondition.status()),
                        addressEq(searchCondition.address()), receiverEq(searchCondition.receiver())
                )
                .leftJoin(deliveryHistory).on(deliveryHistory.delivery.deliveryId.eq(
                        delivery.deliveryId))
                .leftJoin(hubDeliveryHistory).on(hubDeliveryHistory.delivery.deliveryId.eq(
                        delivery.deliveryId))
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(delivery.count())
                .from(delivery)
                .where(deliveryManIdEq(searchCondition.deliveryManId()), deliveryStatusEq(searchCondition.status()),
                        addressEq(searchCondition.address()), receiverEq(searchCondition.receiver())
                )
                .leftJoin(deliveryHistory).on(deliveryHistory.delivery.deliveryId.eq(
                        delivery.deliveryId))
                .leftJoin(hubDeliveryHistory).on(hubDeliveryHistory.delivery.deliveryId.eq(
                        delivery.deliveryId));

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    private BooleanExpression deliveryManIdEq(String deliveryManId) {

        return deliveryManId != null ? delivery.deliveryManId.eq(deliveryManId) : null;
    }

    private BooleanExpression deliveryStatusEq(DeliveryStatus deliveryStatus) {

        return deliveryStatus != null ? delivery.status.eq(deliveryStatus) : null;
    }

    private BooleanExpression addressEq(String address) {

        return address != null ? delivery.address.eq(address) : null;
    }

    private BooleanExpression receiverEq(String receiver) {

        return receiver != null ? delivery.receiver.eq(receiver) : null;
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {

        List<OrderSpecifier> orders = sort.stream().map(o -> {
            com.querydsl.core.types.Order direction =
                    o.isAscending() ? com.querydsl.core.types.Order.ASC
                            : com.querydsl.core.types.Order.DESC;
            String property = o.getProperty();
            PathBuilder<Delivery> orderByExpression = new PathBuilder<>(Delivery.class, "delivery");
            return new OrderSpecifier(direction, orderByExpression.get(property));
        }).toList();

        return orders;
    }
}
