package com.cdcn.apartmentonlinemarket.stores.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cdcn.apartmentonlinemarket.exception.StoreNotfoundException;
import com.cdcn.apartmentonlinemarket.security.jwt.TokenProvider;
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

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public Store getStoreById(UUID id) {
		Optional<Store> store = storeRepository.findById(id);
		return store.orElse(null);
	}

	@Override
	public List<StoreDTO> findAll() {
		List<Store> stores = storeRepository.findAll();
		return mapper.convertToDtoList(stores);
	}

	@Override
	public StoreDTO save(StoreDTO dto) {
		Store store = mapper.convertToEntity(dto);
		store = storeRepository.save(store);
		return mapper.convertToDto(store);
	}

	@Override
	public StoreDTO getStoreByOwner() {
		String userId = tokenProvider.getUserId();
		Store store = storeRepository.findByOwnerId(UUID.fromString(userId)).orElseThrow(() ->
				new StoreNotfoundException(404, "Store not found!"));
		return mapper.convertToDto(store);
	}
}