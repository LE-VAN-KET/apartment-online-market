package com.cdcn.apartmentonlinemarket.orders.controller;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.OrderInfoDto;
import com.cdcn.apartmentonlinemarket.orders.services.OrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/order/info")
@RequiredArgsConstructor
public class OrderInfoController {
    private final OrderInfoService orderInfoService;

    @PutMapping("/{id}")
    public void updateOrderInfo(@Valid @RequestBody OrderInfoDto orderInfoDto,
                                @PathVariable("id") String orderInfoId) {
        orderInfoService.update(orderInfoDto, UUID.fromString(orderInfoId));
    }
}
