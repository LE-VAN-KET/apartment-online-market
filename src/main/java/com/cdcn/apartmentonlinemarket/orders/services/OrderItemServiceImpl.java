package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderItemDto;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderItem;
import com.cdcn.apartmentonlinemarket.orders.domain.mapper.OrderItemMapper;
import com.cdcn.apartmentonlinemarket.orders.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public List<OrderItemDto> saveAll(List<OrderItem> orderItemList) {
        List<OrderItem> orderItems = orderItemRepository.saveAll(orderItemList);
        return orderItems.isEmpty() ? Collections.emptyList() :
                orderItemMapper.convertToDtoList(orderItems);
    }
}
