package com.cdcn.apartmentonlinemarket.carts.domain.entity;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class CartItem extends BaseEntity {

    @Column(columnDefinition = "BINARY(16)")
    private UUID productId;
    private int quantity;
}
