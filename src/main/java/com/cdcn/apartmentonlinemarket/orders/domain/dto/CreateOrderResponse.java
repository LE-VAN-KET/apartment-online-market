package com.cdcn.apartmentonlinemarket.orders.domain.dto;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;
import com.cdcn.apartmentonlinemarket.orders.model.OrderResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderResponse extends OrderResponse {
    private List<OrderItemDto> orderItemDtoList;

    public CreateOrderResponse(UUID id, String reference, BigDecimal totalAmount, OrderStatus orderStatus, Instant expiredAt, List<OrderItemDto> orderItemList) {
        super(id, reference, totalAmount, orderStatus, expiredAt);
        this.orderItemDtoList = orderItemList;
    }

    public CreateOrderResponse(UUID id, String reference, BigDecimal totalAmount, OrderStatus orderStatus, Instant expiredAt) {
        super(id, reference, totalAmount, orderStatus, expiredAt);
    }
}
