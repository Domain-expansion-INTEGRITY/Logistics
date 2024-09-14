package com.domain_expansion.integrity.order.domain.repository;

import com.domain_expansion.integrity.order.domain.model.Order;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
import com.netflix.appinfo.ApplicationInfoManager.InstanceStatusMapper;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQueryRepository {

    Page<Order> findAllByCompanyIdAndCondition(String companyId, Pageable pageable, OrderSearchCondition condition);


    Page<Order> findAllByCompanyIdsAndCondition(List<String> companyIds, Pageable pageable, OrderSearchCondition condition);

    Page<Order> findAllByCondition(Pageable pageable, OrderSearchCondition condition);
}
