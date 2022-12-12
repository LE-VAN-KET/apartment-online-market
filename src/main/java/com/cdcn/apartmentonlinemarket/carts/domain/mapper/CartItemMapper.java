package com.cdcn.apartmentonlinemarket.carts.domain.mapper;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartItemDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.request.CartItemRequest;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.CartItem;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper extends BaseMapper<CartItem, CartItemDto> {
    @Override
    public CartItem convertToEntity(CartItemDto dto, Object... args) {
        return null;
    }

    @Override
    public CartItemDto convertToDto(CartItem entity, Object... args) {
        CartItemDto cartItemDto = new CartItemDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, cartItemDto);
        }
        return cartItemDto;
    }

    public CartItem convertToEntity(CartItemRequest request) {
        CartItem cartItem = new CartItem();
        if (request != null) {
            BeanUtils.copyProperties(request, cartItem);
        }
        return cartItem;
    }
}
