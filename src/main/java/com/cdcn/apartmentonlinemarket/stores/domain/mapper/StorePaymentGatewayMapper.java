package com.cdcn.apartmentonlinemarket.stores.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.stores.domain.dto.StorePaymentGatewayDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;
import com.cdcn.apartmentonlinemarket.stores.service.StoreService;

@Component
public class StorePaymentGatewayMapper extends BaseMapper<StorePaymentGateway, StorePaymentGatewayDTO> {

	@Autowired
	private StoreService storeService;

	@Override
	public StorePaymentGateway convertToEntity(StorePaymentGatewayDTO dto, Object... args) {
		StorePaymentGateway entity = new StorePaymentGateway();
		entity.setEnable(dto.getEnable());
		entity.setGatewayName(dto.getGatewayName());
		entity.setLogo(dto.getLogo());
		entity.setPosition(dto.getPosition());
		if (dto.getstoreId()!=null) {
			Store store = storeService.getStoreById(dto.getstoreId());
			entity.setStore(store);
		}
		return entity;
	}

	@Override
	public StorePaymentGatewayDTO convertToDto(StorePaymentGateway entity, Object... args) {
		StorePaymentGatewayDTO dto = new StorePaymentGatewayDTO();
		dto.setEnable(entity.getEnable());
		dto.setGatewayName(entity.getGatewayName());
		dto.setId(entity.getId());
		dto.setLogo(entity.getLogo());
		dto.setPosition(entity.getPosition());
		if (entity.getStore()!=null) {
			dto.setstoreId(entity.getStore().getId());
		}
		return dto;
	}

}