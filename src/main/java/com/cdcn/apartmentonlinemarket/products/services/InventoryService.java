package com.cdcn.apartmentonlinemarket.products.services;

import com.cdcn.apartmentonlinemarket.products.domain.entity.Inventory;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;

import java.util.UUID;

public interface InventoryService {
    void create(Inventory inventory);
    void add(Product product);
    boolean isInStock(UUID skuCode);
}
