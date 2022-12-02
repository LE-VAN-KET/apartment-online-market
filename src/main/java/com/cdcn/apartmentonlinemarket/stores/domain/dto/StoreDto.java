package com.cdcn.apartmentonlinemarket.stores.domain.dto;

import com.cdcn.apartmentonlinemarket.common.enums.StoreStatus;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class StoreDto {
    private long id;

    private String name;

    private String description;

    private int ownerId;
    private Date establishDate;
    private BigDecimal star;

    private StoreStatus status;

}
