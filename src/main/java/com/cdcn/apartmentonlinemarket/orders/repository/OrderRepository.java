package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.orders.model.OrderResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends IJpaRepository<Orders, UUID> {
    @Query("select NEW com.cdcn.apartmentonlinemarket.orders.model.OrderResponse(order.id, order.reference, sum(oi.quantity * oi.price), order.orderStatus, order.expiredAt) from Orders order LEFT JOIN OrderItem oi ON order.id = oi.orderId where order.reference = :reference")
    OrderResponse findByReference(@Param("reference") String reference);
}
