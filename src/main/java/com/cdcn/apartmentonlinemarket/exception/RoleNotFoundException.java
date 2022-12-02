package com.cdcn.apartmentonlinemarket.exception;

public class RoleNotFoundException extends CommonException{
    public RoleNotFoundException(String message) {
        super(404, message);
    }
}
