package com.cdcn.apartmentonlinemarket.exception;

public class UsernameAlreadyExist extends CommonException{
    public UsernameAlreadyExist() {
        super(400, "Username already exist!");
    }
}
