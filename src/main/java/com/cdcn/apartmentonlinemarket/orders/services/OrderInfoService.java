package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderInfoDto;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderInfo;

import java.util.UUID;

public interface OrderInfoService {
    void create(OrderInfo orderInfo);
    void update(OrderInfoDto orderInfoDto, UUID orderId);
}
