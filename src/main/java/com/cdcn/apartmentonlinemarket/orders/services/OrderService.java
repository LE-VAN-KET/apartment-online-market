package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.orders.domain.dto.CreateOrderResponse;
import com.cdcn.apartmentonlinemarket.orders.domain.dto.request.CreateOrderRequest;
import com.cdcn.apartmentonlinemarket.orders.model.IPNRequest;
import com.cdcn.apartmentonlinemarket.orders.model.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public interface OrderService {
    Response checkout(String order_reference, String return_url) throws UnsupportedEncodingException;
    Response IPN(IPNRequest data) throws UnsupportedEncodingException;
    Response Success(IPNRequest data) throws UnsupportedEncodingException;
    CreateOrderResponse createOrder(CreateOrderRequest request);

    Response orderHistories(UUID user_id);
    Response storeOrderHistories(UUID store_id);
    Response orderDetail(UUID order_id);
    Response storeOrderDetail(UUID order_id, UUID store_id);
}
