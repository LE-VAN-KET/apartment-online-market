package com.cdcn.apartmentonlinemarket.carts.services;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartItemDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.request.CartItemRequest;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.response.AddCartItemResponse;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.CartItem;

import java.util.UUID;

public interface CartService {
    AddCartItemResponse addProductToCard(CartItemRequest cartItemRequest);
    Cart create(Cart cart);
    CartItemDto updateQuantity(CartItemDto cartItemDto);
    void removeCartItem(CartItem cartItem);
    void removeAllCartItem(UUID cartId);
    CartDto getCart();
}
