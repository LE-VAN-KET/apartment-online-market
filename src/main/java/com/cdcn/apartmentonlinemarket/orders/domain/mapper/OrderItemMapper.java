package com.cdcn.apartmentonlinemarket.orders.domain.mapper;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderItemDto;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.request.CreateOrderRequest;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper extends BaseMapper<OrderItem, OrderItemDto> {
    @Override
    public OrderItem convertToEntity(OrderItemDto dto, Object... args) {
        return null;
    }

    @Override
    public OrderItemDto convertToDto(OrderItem entity, Object... args) {
        OrderItemDto orderItemDto = new OrderItemDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, orderItemDto);
        }
        return orderItemDto;
    }

    public List<OrderItem> convertToListEntity(CreateOrderRequest request) {
        List<OrderItem> orderItemList = request.getOrderLineItemList().stream().map(orderLineItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderLineItem.getProductId());
            orderItem.setPrice(orderLineItem.getPrice());
            orderItem.setQuantity(orderLineItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());
        return orderItemList;
    }
}
