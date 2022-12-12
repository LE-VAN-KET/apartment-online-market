package com.cdcn.apartmentonlinemarket.carts.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private UUID id;

    @NotNull
    private UUID productId;

    @Min(0)
    private int quantity;
}
