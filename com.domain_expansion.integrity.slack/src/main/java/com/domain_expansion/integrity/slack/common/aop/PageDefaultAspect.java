package com.domain_expansion.integrity.slack.common.aop;

import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageDefaultAspect {

    private final Integer DEFAULT_PAGE_SIZE = 10;
    private final Set<Integer> defaultPageSizeSet = Set.of(10, 30, 50);

    @Pointcut("@annotation(com.domain_expansion.integrity.slack.common.aop.DefaultPageSize)")
    private void defaultPageSize() {
    }

    @Around("defaultPageSize()")
    public Object checkDefaultPage(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {

            // 만약 Pageable 이라면
            if (args[i] instanceof Pageable) {
                Pageable pageable = (Pageable) args[i];
                int pageSize = pageable.getPageSize();

                if (!defaultPageSizeSet.contains(pageSize)) {
                    args[i] = PageRequest.of(pageable.getPageNumber(), DEFAULT_PAGE_SIZE,
                        pageable.getSort());
                    break;
                }

            }
        }

        return joinPoint.proceed(args);
    }

}
