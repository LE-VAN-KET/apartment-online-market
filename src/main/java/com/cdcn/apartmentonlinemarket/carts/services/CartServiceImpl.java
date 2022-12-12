package com.cdcn.apartmentonlinemarket.carts.services;

import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.CartItemDto;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.request.CartItemRequest;
import com.cdcn.apartmentonlinemarket.carts.domain.dto.response.AddCartItemResponse;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.Cart;
import com.cdcn.apartmentonlinemarket.carts.domain.entity.CartItem;
import com.cdcn.apartmentonlinemarket.carts.domain.mapper.CartItemMapper;
import com.cdcn.apartmentonlinemarket.carts.domain.mapper.CartMapper;
import com.cdcn.apartmentonlinemarket.carts.repository.CartItemRepository;
import com.cdcn.apartmentonlinemarket.carts.repository.CartRepository;
import com.cdcn.apartmentonlinemarket.exception.CartItemNotFoundException;
import com.cdcn.apartmentonlinemarket.exception.ProductNotEnoughException;
import com.cdcn.apartmentonlinemarket.exception.ProductNotFoundException;
import com.cdcn.apartmentonlinemarket.products.repository.ProductRepository;
import com.cdcn.apartmentonlinemarket.products.services.InventoryService;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final TokenProvider tokenProvider;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public AddCartItemResponse addProductToCard(CartItemRequest cartItemRequest) {
        validateProduct(cartItemRequest);
        String userId = tokenProvider.getUserId();
        Cart cart = getCartByUserId(UUID.fromString(userId));
        Optional<CartItem> cartItem = cartItemRepository.findByCartAndProductId(cart, cartItemRequest.getProductId());
        if (cartItem.isPresent()) {
            CartItem existCartItem = cartItem.get();
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemRepository.saveAndFlush(existCartItem);
        } else {
            CartItem newCartItem =  cartItemMapper.convertToEntity(cartItemRequest);
            newCartItem.setCart(cart);
            cartItemRepository.saveAndFlush(newCartItem);
        }

        return AddCartItemResponse.builder().code(201).message("Add product to cart successfully!").build();
    }

    @Override
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public CartItemDto updateQuantity(CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemDto.getId()).orElseThrow(() ->
                new CartItemNotFoundException(404, "Cart Item not found with id"));
        if (cartItemDto.getQuantity() > 0) {
            cartItem.setQuantity(cartItemDto.getQuantity());
            CartItem savedCartItem = cartItemRepository.saveAndFlush(cartItem);
            return cartItemMapper.convertToDto(savedCartItem);
        } else {
            removeCartItem(cartItem);
        }
        return null;
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void removeAllCartItem(UUID cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    @Override
    @Transactional(readOnly = true)
    public CartDto getCart() {
        String userId = tokenProvider.getUserId();
        Cart cart = cartRepository.getAllInformationCartByUserId(UUID.fromString(userId)).orElseThrow(() ->
                new CartItemNotFoundException(404, "Cart not found with user current"));

        return cartMapper.convertToDto(cart);
    }

    private Cart getCartByUserId(UUID userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            return cart.get();
        } else {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return create(newCart);
        }
    }

    private void validateProduct(CartItemRequest cartItemRequest) {
        boolean existProduct = productRepository.existsById(cartItemRequest.getProductId());
        if (!existProduct) {
            throw new ProductNotFoundException(404, "Product not found id");
        }

        boolean isInStock = inventoryService.isInStock(cartItemRequest.getProductId());
        if (!isInStock) {
            throw new ProductNotEnoughException(400, "Product not enough in the inventory!");
        }
    }

}
