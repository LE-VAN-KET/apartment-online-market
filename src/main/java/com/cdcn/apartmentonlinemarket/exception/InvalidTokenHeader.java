package com.cdcn.apartmentonlinemarket.exception;

public class InvalidTokenHeader extends CommonException {
    public InvalidTokenHeader(String message) {
        super(401, message);
    }
}
