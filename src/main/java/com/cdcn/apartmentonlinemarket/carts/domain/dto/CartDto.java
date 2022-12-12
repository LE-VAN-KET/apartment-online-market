package com.cdcn.apartmentonlinemarket.carts.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {
    private UUID id;

    private UUID userId;
    private List<CartItemDto> cartItemDtoList;
}
