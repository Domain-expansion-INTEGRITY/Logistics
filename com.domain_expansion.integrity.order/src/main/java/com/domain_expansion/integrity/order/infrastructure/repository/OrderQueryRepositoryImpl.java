package com.domain_expansion.integrity.order.infrastructure.repository;

import static com.domain_expansion.integrity.order.domain.model.QOrder.order;
import static com.domain_expansion.integrity.order.domain.model.QOrderProduct.*;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.domain.repository.OrderQueryRepository;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
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
public class OrderQueryRepositoryImpl implements OrderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public OrderQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Page<Order> findAllByCompanyIdAndCondition(String companyId, Pageable pageable,
            OrderSearchCondition condition) {

        List<Order> content = queryFactory.select(order)
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where((order.buyerCompanyId.eq(companyId)
                        .or(order.sellerCompanyId.eq(companyId)))
                        .and(sellerCompanyIdEq(condition.sellerCompanyId()))
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())))
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(order.count())
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where((order.buyerCompanyId.eq(companyId)
                        .or(order.sellerCompanyId.eq(companyId)))
                        .and(sellerCompanyIdEq(condition.sellerCompanyId()))
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())));

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    @Override
    public Page<Order> findAllByCompanyIdsAndCondition(List<String> companyIds, Pageable pageable,
            OrderSearchCondition condition) {

        List<Order> content = queryFactory.select(order)
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where((order.buyerCompanyId.in(companyIds)
                        .or(order.sellerCompanyId.in(companyIds)))
                        .and(sellerCompanyIdEq(condition.sellerCompanyId()))
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())))
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(order.count())
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where((order.buyerCompanyId.in(companyIds)
                        .or(order.sellerCompanyId.in(companyIds)))
                        .and(sellerCompanyIdEq(condition.sellerCompanyId()))
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())));

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    @Override
    public Page<Order> findAllByCondition(Pageable pageable, OrderSearchCondition condition) {

        List<Order> content = queryFactory.select(order)
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where(sellerCompanyIdEq(condition.sellerCompanyId())
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())))
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(order.count())
                .from(order)
                .join(orderProduct).on(orderProduct.order.orderId.eq(order.orderId))
                .where(sellerCompanyIdEq(condition.sellerCompanyId())
                        .and(buyerCompanyIdEq(condition.buyerCompanyId()))
                        .and(productIdEq(condition.productId())));

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    public BooleanExpression sellerCompanyIdEq(String sellerCompanyId) {

        return sellerCompanyId != null ? order.sellerCompanyId.eq(sellerCompanyId) : null;
    }

    public BooleanExpression buyerCompanyIdEq(String buyerCompanyId) {

        return buyerCompanyId != null ? order.buyerCompanyId.eq(buyerCompanyId) : null;
    }

    public BooleanExpression productIdEq(String productId) {

        return productId != null ? orderProduct.productInfo.productId.eq(productId) : null;
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {

        List<OrderSpecifier> orders = sort.stream().map(o -> {
            com.querydsl.core.types.Order direction =
                    o.isAscending() ? com.querydsl.core.types.Order.ASC
                            : com.querydsl.core.types.Order.DESC;
            String property = o.getProperty();
            PathBuilder<Order> orderByExpression = new PathBuilder<>(Order.class, "order");
            return new OrderSpecifier(direction, orderByExpression.get(property));
        }).toList();

        return orders;
    }
}
