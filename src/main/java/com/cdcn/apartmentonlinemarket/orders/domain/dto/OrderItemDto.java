package com.cdcn.apartmentonlinemarket.orders.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private UUID orderItemId;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private BigDecimal price;
}
