package com.cdcn.apartmentonlinemarket.products.services.impl;

import com.cdcn.apartmentonlinemarket.exception.InventoryNotFoundException;
import com.cdcn.apartmentonlinemarket.exception.ProductNotEnoughException;
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
    public void create(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public void add(Product product) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(product.getId());
        inventory.setQuantity(product.getQuantity());
        create(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(UUID skuCode) {
        Inventory inventory =  getBySkuCode(skuCode);
        return inventory.getQuantity() > 0;
    }

    @Override
    @Transactional
    public void descreaseQuantityStock(UUID skuCode, int quantity) {
        Inventory inventory = getBySkuCode(skuCode);
        if (inventory.getQuantity() < quantity) {
            throw new ProductNotEnoughException(400, "Product not enough in the inventory!");
        } else {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
        }
    }

    private Inventory getBySkuCode(UUID skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).orElseThrow(() ->
                new InventoryNotFoundException(404, "Inventory not found skuCode!"));
    }
}
