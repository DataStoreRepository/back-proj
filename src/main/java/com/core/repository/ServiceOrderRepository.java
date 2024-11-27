package com.core.repository;

import com.core.entity.ServiceOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findByUserId(Long userId);

    List<ServiceOrder> findByOfferedServiceIdInOrderByRatingDesc(List<Long> offeredServiceIds);

    List<ServiceOrder> findByServiceProviderId(Long providerId);

}