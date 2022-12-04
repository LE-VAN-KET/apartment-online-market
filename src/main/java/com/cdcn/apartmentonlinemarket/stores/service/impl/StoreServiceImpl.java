package com.cdcn.apartmentonlinemarket.stores.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.domain.mapper.StoreMapper;
import com.cdcn.apartmentonlinemarket.stores.repository.StoreRepository;
import com.cdcn.apartmentonlinemarket.stores.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService{
	
	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMapper mapper;
	
	@Override
	public Store getStoreById(UUID id) {
		Optional<Store> store = storeRepository.findById(id);
		return store.isEmpty()?null:store.get();
	}

	@Override
	public List<StoreDTO> findAll() {
		List<Store> stores = storeRepository.findAll();
		List<StoreDTO> lst = mapper.convertToDtoList(stores);
		return lst;
	}

	@Override
	public StoreDTO save(StoreDTO dto) {
		Store store = mapper.convertToEntity(dto);
		store = storeRepository.save(store);
		return mapper.convertToDto(store);
	}
}
