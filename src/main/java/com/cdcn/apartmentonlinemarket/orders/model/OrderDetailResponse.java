package com.cdcn.apartmentonlinemarket.orders.model;

import com.cdcn.apartmentonlinemarket.common.enums.OrderStatus;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentMethod;
import com.cdcn.apartmentonlinemarket.common.enums.PaymentStatus;
import com.cdcn.apartmentonlinemarket.payments.domain.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailResponse {
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod type;

    private BigDecimal totalPaid;
    private String details;
    private String reference;
    private String currentCode;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    private OffsetDateTime paidDate;
    private String customerName;
    private Long roomId;

    public OrderDetailResponse(UUID id, PaymentMethod type, BigDecimal totalPaid, String details, String reference, String currentCode, PaymentStatus status, OrderStatus orderStatus, OffsetDateTime createdDate, String customerName, Long roomId) {
        this.id = id;
        this.type = type;
        this.totalPaid = totalPaid;
        this.details = details;
        this.reference = reference;
        this.currentCode = currentCode;
        this.paymentStatus = status;
        this.orderStatus = orderStatus;
        this.paidDate = createdDate;
        this.customerName = customerName;
        this.roomId = roomId;
    }
    private List<OrderItemResponse> Items;
}
