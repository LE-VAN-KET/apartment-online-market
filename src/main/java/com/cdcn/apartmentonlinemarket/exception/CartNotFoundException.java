package com.cdcn.apartmentonlinemarket.exception;

public class CartNotFoundException extends CommonException{
    public CartNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
