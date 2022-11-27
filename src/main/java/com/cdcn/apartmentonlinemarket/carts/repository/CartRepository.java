package com.cdcn.apartmentonlinemarket.carts.repository;

import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends IJpaRepository<Cart, UUID> {
}
