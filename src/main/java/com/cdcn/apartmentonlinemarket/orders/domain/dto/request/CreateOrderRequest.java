package com.cdcn.apartmentonlinemarket.orders.domain.dto.request;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CreateOrderRequest {
    List<OrderLineItem> orderLineItemList;
}
