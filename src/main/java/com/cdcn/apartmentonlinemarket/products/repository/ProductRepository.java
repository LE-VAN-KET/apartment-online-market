package com.cdcn.apartmentonlinemarket.products.repository;

import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends IJpaRepository<Product, UUID> {
    boolean existsById(UUID id);
    List<Product> findAllByStore(Store store);
}
