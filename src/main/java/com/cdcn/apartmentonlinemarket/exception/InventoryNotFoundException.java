package com.cdcn.apartmentonlinemarket.exception;

public class InventoryNotFoundException extends CommonException{
    public InventoryNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
