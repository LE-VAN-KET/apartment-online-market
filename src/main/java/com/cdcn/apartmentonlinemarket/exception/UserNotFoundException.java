package com.cdcn.apartmentonlinemarket.exception;

public class UserNotFoundException extends CommonException{
    public UserNotFoundException(Integer code, String message) {
        super(code, message);
    }
}
