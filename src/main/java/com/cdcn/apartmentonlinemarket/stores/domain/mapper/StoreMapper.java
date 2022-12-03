package com.cdcn.apartmentonlinemarket.stores.domain.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdcn.apartmentonlinemarket.common.enums.StoreStatus;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.repository.ProductRepository;
import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;
import com.cdcn.apartmentonlinemarket.stores.repository.StorePaymentGatewayRepository;

@Component
public class StoreMapper extends BaseMapper<Store, StoreDTO>{

	@Override
	public Store convertToEntity(StoreDTO dto, Object... args) {
		Store store = new Store();
		store.setDescription(dto.getDescription());
		store.setEstablishDate(dto.getEstablishDate());
		store.setName(dto.getName());
		store.setOwnerId(dto.getOwnerId());
		store.setStar(dto.getStar());
		return store;
	}

	@Override
	public StoreDTO convertToDto(Store entity, Object... args) {
		StoreDTO dto = new StoreDTO();
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setEstablishDate(entity.getEstablishDate());
		dto.setName(entity.getName());
		dto.setOwnerId(entity.getOwnerId());
		dto.setStar(entity.getStar());
		if (entity.getStatus()!=null) {
			dto.setStatus(entity.getStatus().toString());

		}
		if (entity.getProducts()!=null) {
			List<UUID> products_id = new ArrayList<UUID>(); 
			for (Product item : entity.getProducts()) {
				products_id.add(item.getId());
			}
			dto.setProducts_id(products_id);
		}
		if (entity.getStorePaymentGateways()!=null) {
			List<UUID> gateways_id = new ArrayList<UUID>(); 
			for (StorePaymentGateway item : entity.getStorePaymentGateways()) {
				gateways_id.add(item.getId());
			}
			dto.setPayment_gateways_id(gateways_id);
		}
		
		return dto;
	}

}
