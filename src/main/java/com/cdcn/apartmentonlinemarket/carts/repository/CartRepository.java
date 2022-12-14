package com.cdcn.apartmentonlinemarket.carts.repository;

import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends IJpaRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);

    @Query("select cart from Cart cart join fetch cart.cartItemSet where cart.userId = :userId")
    Optional<Cart> getAllInformationCartByUserId(@Param("userId") UUID userId);
}
