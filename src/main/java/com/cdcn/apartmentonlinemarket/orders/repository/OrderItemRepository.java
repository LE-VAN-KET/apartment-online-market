package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends IJpaRepository<OrderItem, UUID> {
}
