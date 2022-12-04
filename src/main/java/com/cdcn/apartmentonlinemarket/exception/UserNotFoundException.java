package com.cdcn.apartmentonlinemarket.exception;

public class UserNotFoundException extends CommonException{
    public UserNotFoundException(String message) {
        super(404, message);
    }
}
