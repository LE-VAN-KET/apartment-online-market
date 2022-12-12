package com.cdcn.apartmentonlinemarket.carts.repository;

import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.CartItem;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends IJpaRepository<CartItem, UUID> {
    Optional<CartItem> findByCartAndProductId(Cart cart, UUID productId);

    @Modifying
    @Query("delete from CartItem cartItem where cartItem.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") UUID cartId);
}
