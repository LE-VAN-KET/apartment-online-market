package com.cdcn.apartmentonlinemarket.orders.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.UUID;

@Entity
@Table(name = "order_info")
@Getter
@Setter
@NoArgsConstructor
public class OrderInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Email
    private String email;
    private String customerName;
    @Column(length = 12)
    private String phone;
    private Long roomId;
}
