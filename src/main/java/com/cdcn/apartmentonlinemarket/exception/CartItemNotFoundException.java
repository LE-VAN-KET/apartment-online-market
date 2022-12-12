package com.cdcn.apartmentonlinemarket.exception;

public class CartItemNotFoundException extends CommonException{
    public CartItemNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
