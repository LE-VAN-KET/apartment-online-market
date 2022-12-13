package com.cdcn.apartmentonlinemarket.exception;

public class CategoryNotFoundException extends CommonException{
    public CategoryNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
