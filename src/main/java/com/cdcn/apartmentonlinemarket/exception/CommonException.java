package com.cdcn.apartmentonlinemarket.exception;

public class CommonException extends RuntimeException{
    private final Integer code;
    private final String message;

    public CommonException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
