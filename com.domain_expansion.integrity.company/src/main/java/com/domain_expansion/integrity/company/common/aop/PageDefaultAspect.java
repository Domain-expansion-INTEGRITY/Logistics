package com.domain_expansion.integrity.company.common.aop;

import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageDefaultAspect {

    private Set<Integer> defaultPageSizes = Set.of(10, 30, 50);
    private Integer defaultPageSize = 10;

    @Around("@annotation(DefaultPageSize)")
    public Object checkPageSize(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Pageable) {
                Pageable pageable = (Pageable) args[i];
                int pageSize = pageable.getPageSize();

                if (!defaultPageSizes.contains(pageSize)) {
                    args[i] = PageRequest.of(pageable.getPageNumber(), defaultPageSize, pageable.getSort());
                    break;
                }
            }
        }

        return joinPoint.proceed();
    }
}
