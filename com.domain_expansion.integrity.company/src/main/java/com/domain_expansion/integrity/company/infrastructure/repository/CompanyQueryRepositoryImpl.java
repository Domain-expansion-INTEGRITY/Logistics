package com.domain_expansion.integrity.company.infrastructure.repository;

import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.domain.model.QCompany;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /***
     * created_at 기준으로 내림차순설정
     * TODO : 추후 접근권한 별로 검색조건 추가
     * @param companyName
     * @param type
     * @param pageable
     * @return
     */
    @Override
    public Page<CompanyResponseDto> searchCompanies(String companyName, CompanyType type,
            Pageable pageable) {

        QCompany qCompany = QCompany.company;

        BooleanBuilder builder = new BooleanBuilder();

        if(companyName != null && !companyName.isEmpty()) {
            builder.and(qCompany.company.name.containsIgnoreCase(companyName));
        }

        if(type != null) {
            builder.and(qCompany.companyType.eq(type));
        }

        JPAQuery<Company> query = jpaQueryFactory.selectFrom(qCompany)
                .where(builder)
                .orderBy(qCompany.createdAt.desc());

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        List<CompanyResponseDto> companies = query.stream().map(CompanyResponseDto::from).toList();

        return new PageImpl<>(companies, pageable, total);
    }
}
