package com.cdcn.apartmentonlinemarket.carts.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddCartItemResponse {
    private int code;
    private String message;
}
