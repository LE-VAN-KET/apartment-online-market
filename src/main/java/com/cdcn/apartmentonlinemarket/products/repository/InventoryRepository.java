package com.cdcn.apartmentonlinemarket.products.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Inventory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends IJpaRepository<Inventory, UUID> {
    Optional<Inventory> findBySkuCode(UUID skuCode);
}
