package com.core.repository;

import com.core.entity.OfferedService;
import com.core.entity.ServiceProvider;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferedServiceRepository extends JpaRepository<OfferedService, Long> {

    List<OfferedService> findByServiceProviderInAndNameContainingIgnoreCase(List<ServiceProvider> serviceProviders,
            String name);
}