package com.cdcn.apartmentonlinemarket.stores.service;

import java.util.List;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StorePaymentGatewayDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;

public interface StorePaymentGatewayService {
	List<StorePaymentGatewayDTO> findAll();
	StorePaymentGateway save(StorePaymentGatewayDTO dto);

}
