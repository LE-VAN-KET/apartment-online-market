package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderInfoRepository extends IJpaRepository<OrderInfo, UUID> {
    Optional<OrderInfo> findByOrderId(UUID orderId);
}
