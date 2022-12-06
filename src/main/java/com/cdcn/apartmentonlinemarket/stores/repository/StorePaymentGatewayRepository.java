package com.cdcn.apartmentonlinemarket.stores.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;

@Repository
public interface StorePaymentGatewayRepository extends IJpaRepository<StorePaymentGateway, UUID>{

}