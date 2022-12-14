package com.cdcn.apartmentonlinemarket.stores.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends IJpaRepository<Store, UUID> {
    Optional<Store> findByOwnerId(UUID ownerId);
    Optional<Store> findByIdAndOwnerId(UUID id, UUID ownerId);
}
