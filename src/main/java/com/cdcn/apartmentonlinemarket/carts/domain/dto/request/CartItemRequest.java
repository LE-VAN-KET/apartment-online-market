package com.cdcn.apartmentonlinemarket.carts.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CartItemRequest {
    @NotNull
    private UUID productId;

    @Min(1)
    private int quantity;
}
