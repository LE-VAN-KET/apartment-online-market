package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.orders.model.OrderResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface OrderRepository extends IJpaRepository<Orders, UUID> {
    @Query("select NEW com.cdcn.apartmentonlinemarket.orders.model.OrderResponse(order.id, order.reference, sum(oi.quantity * oi.price), order.orderStatus) from Orders order LEFT JOIN OrderItem oi ON order.id = oi.orderId where order.reference = :reference")
    OrderResponse findByReference(@Param("reference") String reference);

//    @Modifying
//    @Query("update Orders o set o.orderStatus = :status where o.expiredAt < :timeNow")
//    void cancelOrderExpired(@Param("status")OrderStatus status, @Param("timeNow") Timestamp timeNow);
}
