package com.cdcn.apartmentonlinemarket.carts.controller;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartItemDto;
import com.cdcn.apartmentonlinemarket.carts.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemController {
    private final CartService cartService;

    @Autowired
    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SELLER')")
    public CartItemDto updateQuantityCartItem(@Valid @RequestBody CartItemDto cartItemDto,
                                              @PathVariable("id") String cartItemId) {
        cartItemDto.setId(UUID.fromString(cartItemId));
        return cartService.updateQuantity(cartItemDto);
    }
}
