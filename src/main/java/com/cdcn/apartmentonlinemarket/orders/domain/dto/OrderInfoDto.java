package com.cdcn.apartmentonlinemarket.orders.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderInfoDto {
    private UUID id;

    private UUID orderId;

    private String email;
    private String customerName;
    private String phone;
    private Long roomId;
}
