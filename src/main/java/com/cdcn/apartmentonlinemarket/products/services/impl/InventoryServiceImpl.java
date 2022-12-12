package com.cdcn.apartmentonlinemarket.products.services.impl;

import com.cdcn.apartmentonlinemarket.exception.InventoryNotFoundException;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Inventory;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.repository.InventoryRepository;
import com.cdcn.apartmentonlinemarket.products.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public void create(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Override
    public void add(Product product) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(product.getId());
        inventory.setQuantity(product.getQuantity());
        create(inventory);
    }

    @Override
    public boolean isInStock(UUID skuCode) {
        Inventory inventory =  inventoryRepository.findBySkuCode(skuCode).orElseThrow(() ->
                new InventoryNotFoundException(404, "Inventory not found skuCode!"));
        return inventory.getQuantity() > 0;
    }
}
