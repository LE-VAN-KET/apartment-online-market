package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.payments.domain.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends IJpaRepository<Payment, UUID> {
}
