package com.cdcn.apartmentonlinemarket.orders.controller;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.CreateOrderResponse;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.request.CreateOrderRequest;
import com.cdcn.apartmentonlinemarket.orders.model.IPNRequest;
import com.cdcn.apartmentonlinemarket.orders.model.Response;
import com.cdcn.apartmentonlinemarket.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("{order_reference}")
    public Response checkout(@PathVariable String order_reference) throws  UnsupportedEncodingException {
        return orderService.checkout(order_reference);
    }
    @GetMapping("ipn")
    public Response IPN(IPNRequest data) throws UnsupportedEncodingException {
        return orderService.IPN(data);
    }

    @GetMapping("return")
    public Response ReturnUrl(IPNRequest data) throws UnsupportedEncodingException {
        return orderService.Success(data);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SELLER')")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

}
