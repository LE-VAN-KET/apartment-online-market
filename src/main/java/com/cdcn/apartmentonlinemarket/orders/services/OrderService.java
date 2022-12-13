package com.cdcn.apartmentonlinemarket.orders.services;

import com.cdcn.apartmentonlinemarket.orders.model.IPNRequest;
import com.cdcn.apartmentonlinemarket.orders.model.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface OrderService {
    Response checkout(String order_reference) throws UnsupportedEncodingException;
    Response IPN(IPNRequest data) throws UnsupportedEncodingException;
    Response Success(IPNRequest data) throws UnsupportedEncodingException;
}
