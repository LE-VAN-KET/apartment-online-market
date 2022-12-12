package com.cdcn.apartmentonlinemarket.carts.domain.mapper;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartDto;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CartMapper extends BaseMapper<Cart, CartDto> {
    private final CartItemMapper cartItemMapper;

    public CartMapper() {
        cartItemMapper = new CartItemMapper();
    }

    @Override
    public Cart convertToEntity(CartDto dto, Object... args) {
        return null;
    }

    @Override
    public CartDto convertToDto(Cart entity, Object... args) {
        CartDto cartDto = new CartDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, cartDto);
            if (entity.getCartItemSet() != null && !entity.getCartItemSet().isEmpty()) {
                cartDto.setCartItemDtoList(cartItemMapper.convertToDtoList(entity.getCartItemSet()));
            }
        }
        return cartDto;
    }
}
