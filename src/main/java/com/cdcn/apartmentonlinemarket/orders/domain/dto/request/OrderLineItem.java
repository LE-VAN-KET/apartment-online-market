package com.cdcn.apartmentonlinemarket.orders.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderLineItem {
    private UUID productId;
    private BigDecimal price;
    private Integer quantity;
}
