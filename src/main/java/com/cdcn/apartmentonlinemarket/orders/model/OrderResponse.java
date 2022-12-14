package com.cdcn.apartmentonlinemarket.orders.model;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private UUID id;
    private String reference;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

//    private Timestamp expiredAt;
    public OrderResponse(UUID id, String reference, Long totalAmount, OrderStatus orderStatus){
        this.id = id;
        this.reference = reference;
        this.totalAmount = new BigDecimal(totalAmount);
        this.orderStatus = orderStatus;
//        this.expiredAt = expiredAt;
    }
}
