package com.domain_expansion.integrity.product.infrastructure.repository;

import static com.domain_expansion.integrity.product.domain.model.QProduct.product;

import com.domain_expansion.integrity.product.domain.model.Product;
import com.domain_expansion.integrity.product.domain.repository.ProductQueryRepository;
import com.domain_expansion.integrity.product.presentation.request.ProductSearchCondition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Page<Product> findAllByCondition(Pageable pageable,
            ProductSearchCondition searchCondition) {

        List<Product> content = queryFactory.selectFrom(product)
                .where(productNameContains(searchCondition.productName()), productIdContains(
                        searchCondition.productId()))
                .orderBy(getOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = queryFactory.select(product.count())
                .from(product)
                .where(productNameContains(searchCondition.productName()),
                        productIdContains(searchCondition.productId()));

        return PageableExecutionUtils.getPage(content, pageable, total::fetchOne);
    }

    private BooleanExpression productIdContains(String productId) {

        return productId != null ? product.productId.contains(productId) : null;
    }

    private BooleanExpression productNameContains(String productName) {

        return productName != null ? product.name.value.contains(productName) : null;
    }

    private List<OrderSpecifier> getOrderSpecifiers(Sort sort) {

        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(o -> {
            com.querydsl.core.types.Order direction =
                    o.isAscending() ? com.querydsl.core.types.Order.ASC
                            : com.querydsl.core.types.Order.DESC;
            String property = o.getProperty();
            PathBuilder<Product> orderByExpression = new PathBuilder<>(Product.class, "product");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(property)));
        });

        return orders;
    }
}
