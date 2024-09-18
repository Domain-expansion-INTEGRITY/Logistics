package com.domain_expansion.integrity.user.infrastructure.repository;

import static com.domain_expansion.integrity.user.domain.model.QUser.user;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.model.vo.UserPhoneNumber;
import com.domain_expansion.integrity.user.domain.repository.UserQueryRepository;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Boolean checkExistFieldInfo(Long userId, String phoneNumber, String slackId) {

        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(phoneNumber)) {
            builder.or(user.phoneNumber.eq(new UserPhoneNumber(phoneNumber)));
        }

        if (StringUtils.hasText(slackId)) {
            builder.or(user.slackId.eq(slackId));
        }

        Long count = query
            .select(user.count())
            .from(user)
            .where(builder.and(user.id.ne(userId)))
            .fetchOne();

        return count != null && count > 0;

    }

    @Override
    public Page<User> findAllUserByCondition(Pageable pageable,
        UserSearchCondition searchCondition) {

        BooleanBuilder builder = new BooleanBuilder();

        if (searchCondition.role() != null) {
            builder.and(user.role.eq(searchCondition.role()));
        }

        pageable.getSort();

        List<User> fetch = query.selectFrom(user)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(sortUser(pageable).toArray(OrderSpecifier[]::new))
            .fetch();

        JPAQuery<Long> total = query.select(user.count())
            .from(user)
            .where(builder);

        return PageableExecutionUtils.getPage(fetch, pageable, total::fetchOne);
    }

    /**
     * user 정렬
     */
    private List<OrderSpecifier> sortUser(Pageable pageable) {

        if (pageable.getSort().isEmpty()) {
            return List.of(new OrderSpecifier<>(Order.DESC, user.createdAt));
        }

        return pageable.getSort().map(order -> {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            PathBuilder<User> userPath = new PathBuilder<>(User.class, "user");

            return new OrderSpecifier(direction,
                userPath.get(order.getProperty()));

        }).toList();
    }
}

