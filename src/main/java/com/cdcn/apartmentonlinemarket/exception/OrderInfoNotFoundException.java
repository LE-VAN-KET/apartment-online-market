package com.cdcn.apartmentonlinemarket.exception;

public class OrderInfoNotFoundException extends CommonException{

    public OrderInfoNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
