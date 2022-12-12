package com.cdcn.apartmentonlinemarket.carts.controller;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.request.CartItemRequest;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.response.AddCartItemResponse;
import com.cdcn.apartmentonlinemarket.carts.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SELLER')")
    public AddCartItemResponse addProductToCart(@Valid @RequestBody CartItemRequest request) {
        return cartService.addProductToCard(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SELLER')")
    public void removeAllCartItem(@PathVariable("id") String cartId) {
        cartService.removeAllCartItem(UUID.fromString(cartId));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SELLER')")
    public CartDto getCart() {
        return cartService.getCart();
    }

}
