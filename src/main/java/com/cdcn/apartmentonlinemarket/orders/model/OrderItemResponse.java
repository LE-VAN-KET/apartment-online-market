package com.cdcn.apartmentonlinemarket.orders.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class OrderItemResponse {
    private UUID orderItemId;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

    public OrderItemResponse(UUID orderItemId, UUID productId, int quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
