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
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("{order_reference}")
    public Response checkout(@PathVariable String order_reference, @RequestParam("return_url") String return_url) throws  UnsupportedEncodingException {
        return orderService.checkout(order_reference, return_url);
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

    @GetMapping("/histories/{user_id}")
    public Response orderHistory(@PathVariable("user_id") UUID user_id)   {
        return orderService.orderHistories(user_id);
    }

    @GetMapping("/details/{order_id}")
    public Response orderDetail(@PathVariable("order_id") UUID order_id)   {
        return orderService.orderDetail(order_id);
    }
    @GetMapping("/store/histories/{store_id}")
    public Response storeOsrderHistory(@PathVariable("store_id") UUID store_id)   {
        return orderService.storeOrderHistories(store_id);
    }
    @GetMapping("/store/{store_id}/detail/{order_id}")
    public Response storeOrderDetail(@PathVariable("store_id") UUID store_id, @PathVariable("order_id") UUID order_id)   {
        return orderService.storeOrderDetail(order_id,store_id);
    }
}
