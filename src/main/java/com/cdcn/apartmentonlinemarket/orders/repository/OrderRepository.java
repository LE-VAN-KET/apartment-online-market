package com.cdcn.apartmentonlinemarket.orders.repository;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.repository.IJpaRepository;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import com.cdcn.apartmentonlinemarket.orders.model.OrderDetailResponse;
import com.cdcn.apartmentonlinemarket.orders.model.OrderHistoryResponse;
import com.cdcn.apartmentonlinemarket.orders.model.OrderItemResponse;
import com.cdcn.apartmentonlinemarket.orders.model.OrderResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends IJpaRepository<Orders, UUID> {
    @Query("select NEW com.cdcn.apartmentonlinemarket.orders.model.OrderResponse(order.id, order.reference, sum(oi.quantity * oi.price), order.orderStatus) from Orders order LEFT JOIN OrderItem oi ON order.id = oi.orderId where order.reference = :reference")
    OrderResponse findByReference(@Param("reference") String reference);

//    @Modifying
//    @Query("update Orders o set o.orderStatus = :status where o.expiredAt < :timeNow")
//    void cancelOrderExpired(@Param("status")OrderStatus status, @Param("timeNow") Timestamp timeNow);

    @Query("select new com.cdcn.apartmentonlinemarket.orders.model.OrderHistoryResponse(o.id, o.reference, SUM(oit.price * oit.quantity), o.orderStatus, oi.customerName, oi.roomId) FROM Orders o LEFT JOIN OrderInfo oi ON o.id = oi.orderId LEFT JOIN OrderItem oit ON o.id = oit.orderId WHERE o.user.id = :user_id GROUP BY o.id, o.reference, o.orderStatus, oi.customerName, oi.roomId ORDER BY o.createAt DESC")
    List<OrderHistoryResponse> orderHistories(@Param("user_id") UUID user_id);

    @Query("select new com.cdcn.apartmentonlinemarket.orders.model.OrderDetailResponse(o.id, p.type, p.totalAmount, p.details, o.reference, p.currentCode, p.status,o.orderStatus, p.createdDate, oi.customerNumber,oi.customerName, oi.roomId) FROM Orders o LEFT JOIN OrderInfo oi ON o.id = oi.orderId LEFT JOIN Payment p ON p.orderId = o.id LEFT JOIN OrderItem oit ON o.id = oit.orderId WHERE o.id = :order_id GROUP BY o.id, p.type, p.totalAmount, p.details, o.reference, p.currentCode, p.status,o.orderStatus, p.createdDate, oi.customerNumber,oi.customerName, oi.roomId ORDER BY  o.createAt DESC")
    OrderDetailResponse orderDetail(@Param("order_id") UUID order_id);

    @Query("select new com.cdcn.apartmentonlinemarket.orders.model.OrderItemResponse(oi.orderItemId, oi.productId, oi.quantity, oi.price) from OrderItem oi where  oi.orderId = :order_id")
    List<OrderItemResponse> orderItems(@Param("order_id") UUID order_id);

    @Query("select new com.cdcn.apartmentonlinemarket.orders.model.OrderHistoryResponse(o.id, o.reference, SUM(oit.price * oit.quantity), o.orderStatus, oi.customerName, oi.roomId) FROM Orders o LEFT JOIN OrderInfo oi ON o.id = oi.orderId LEFT JOIN OrderItem oit ON o.id = oit.orderId LEFT JOIN Product p on oit.productId = p.id LEFT JOIN Store s ON s.id = p.store.id WHERE s.id = :store_id GROUP BY o.id, o.reference, o.orderStatus, oi.customerName, oi.roomId ORDER BY o.createAt DESC")
    List<OrderHistoryResponse> storeOrderHistories(@Param("store_id") UUID store_id);

    @Query("select new com.cdcn.apartmentonlinemarket.orders.model.OrderItemResponse(oi.orderItemId, oi.productId, oi.quantity, oi.price) from OrderItem oi LEFT JOIN Product p on oi.productId = p.id LEFT JOIN Store s ON s.id = p.store.id where  oi.orderId = :order_id AND s.id = :store_id")
    List<OrderItemResponse> storeOrderItems(@Param("order_id") UUID order_id, @Param("store_id") UUID store_id);
}
