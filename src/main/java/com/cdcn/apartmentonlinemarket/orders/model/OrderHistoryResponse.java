package com.cdcn.apartmentonlinemarket.orders.model;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.TimestampType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
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
    private String customerName;
    private String phone;
    private String email;
    private Long roomId;

    public OrderHistoryResponse(UUID id, String reference, Long totalAmount, OrderStatus orderStatus, String customerName, String phone, String email, Long roomId) {
        this.id = id;
        this.reference = reference;
        this.totalAmount = new BigDecimal(totalAmount);
        this.orderStatus = orderStatus;
        this.phone = phone;
        this.email = email;
        this.customerName = customerName;
        this.roomId = roomId;
    }
}
