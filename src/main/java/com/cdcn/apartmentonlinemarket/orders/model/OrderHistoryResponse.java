package com.cdcn.apartmentonlinemarket.orders.model;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderHistoryResponse {
    private UUID id;
    private String reference;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    //    private Timestamp createAt;
    private String customerNumber;
    private String customerName;
    private Long roomId;

    public OrderHistoryResponse(UUID id, String reference, Long totalAmount, OrderStatus orderStatus, String customerNumber, String customerName, Long roomId) {
        this.id = id;
        this.reference = reference;
        this.totalAmount = new BigDecimal(totalAmount);
        this.orderStatus = orderStatus;
//        this.createAt = createAt;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.roomId = roomId;
    }
}
