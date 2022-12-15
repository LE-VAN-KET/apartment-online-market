package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.common.util.Properties;
import com.cdcn.apartmentonlinemarket.exception.OrderInfoNotFoundException;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderInfoDto;
import com.cdcn.apartmentonlinemarket.orders.domain.entity.OrderInfo;
import com.cdcn.apartmentonlinemarket.orders.repository.OrderInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService{
    private final OrderInfoRepository orderInfoRepository;

    @Override
    public void create(OrderInfo orderInfo) {
        orderInfoRepository.save(orderInfo);
    }

    @Override
    public void update(OrderInfoDto orderInfoDto, UUID orderId) {
        OrderInfo orderInfoExist = orderInfoRepository.findByOrderId(orderId).orElseThrow(() ->
                new OrderInfoNotFoundException(404, "Order info not found!"));
        BeanUtils.copyProperties(orderInfoDto, orderInfoExist, Properties.getNullPropertyNames(orderInfoDto));
        orderInfoRepository.save(orderInfoExist);
    }
}
