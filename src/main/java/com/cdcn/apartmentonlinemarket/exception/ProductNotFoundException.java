package com.cdcn.apartmentonlinemarket.exception;

public class ProductNotFoundException extends CommonException{
    public ProductNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
