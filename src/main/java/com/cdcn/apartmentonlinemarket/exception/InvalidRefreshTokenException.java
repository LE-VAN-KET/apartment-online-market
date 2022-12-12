package com.cdcn.apartmentonlinemarket.exception;

public class InvalidRefreshTokenException extends CommonException{
    public InvalidRefreshTokenException(Integer code, String message) {
        super(code, message);
    }
}
