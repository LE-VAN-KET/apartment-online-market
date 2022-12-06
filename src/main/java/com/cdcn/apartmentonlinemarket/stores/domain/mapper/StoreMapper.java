package com.cdcn.apartmentonlinemarket.stores.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;

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
			List<UUID> productsIds = new ArrayList<>(); 
			for (Product item : entity.getProducts()) {
				productsIds.add(item.getId());
			}
			dto.setproductIds(productsIds);
		}
		if (entity.getStorePaymentGateways()!=null) {
			List<UUID> gatewayIds = new ArrayList<>(); 
			for (StorePaymentGateway item : entity.getStorePaymentGateways()) {
				gatewayIds.add(item.getId());
			}
			dto.setpaymentGatewayIds(gatewayIds);
		}
		return dto;
	}

}