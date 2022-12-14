package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderItemDto;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> saveAll(List<OrderItem> orderItemList);
}
