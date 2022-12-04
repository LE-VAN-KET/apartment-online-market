package com.cdcn.apartmentonlinemarket.stores.service;

import java.util.List;
import java.util.UUID;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;

public interface StoreService {
	Store getStoreById(UUID id);
	List<StoreDTO> findAll();
	StoreDTO save(StoreDTO dto);
}
