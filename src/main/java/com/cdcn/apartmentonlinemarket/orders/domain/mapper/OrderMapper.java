package com.cdcn.apartmentonlinemarket.orders.domain.mapper;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderDto;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.request.CreateOrderRequest;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.Orders;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper extends BaseMapper<Orders, OrderDto> {
    @Override
    public Orders convertToEntity(OrderDto dto, Object... args) {
        return null;
    }

    @Override
    public OrderDto convertToDto(Orders entity, Object... args) {
        return null;
    }

}
