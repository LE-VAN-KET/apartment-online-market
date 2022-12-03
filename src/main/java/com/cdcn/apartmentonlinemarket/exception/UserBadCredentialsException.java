package com.cdcn.apartmentonlinemarket.exception;

public class UserBadCredentialsException extends CommonException{
    public UserBadCredentialsException(String message) {
        super(400, message);
    }
}
