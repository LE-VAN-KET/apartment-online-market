package com.cdcn.apartmentonlinemarket.exception;

public class ProductNotEnoughException extends CommonException{
    public ProductNotEnoughException(Integer code, String message) {
        super(code, message);
    }
}
