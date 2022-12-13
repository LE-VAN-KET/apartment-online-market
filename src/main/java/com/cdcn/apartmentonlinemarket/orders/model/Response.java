package com.cdcn.apartmentonlinemarket.orders.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String RspCode;
    private String Message;
    private Object data;
}
