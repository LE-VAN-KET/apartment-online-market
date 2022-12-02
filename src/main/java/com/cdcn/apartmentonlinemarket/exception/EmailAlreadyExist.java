package com.cdcn.apartmentonlinemarket.exception;

public class EmailAlreadyExist extends CommonException{
    public EmailAlreadyExist() {
        super(400, "Email already exist~!");
    }
}
