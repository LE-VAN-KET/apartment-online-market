package com.cdcn.apartmentonlinemarket.stores.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StorePaymentGatewayDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;
import com.cdcn.apartmentonlinemarket.stores.domain.mapper.StorePaymentGatewayMapper;
import com.cdcn.apartmentonlinemarket.stores.repository.StorePaymentGatewayRepository;
import com.cdcn.apartmentonlinemarket.stores.service.StorePaymentGatewayService;

@Service
public class StorePaymentGatewayServiceImpl implements StorePaymentGatewayService{

	@Autowired
	private StorePaymentGatewayRepository repository;

	@Autowired
	private StorePaymentGatewayMapper mapper;

	@Override
	public List<StorePaymentGatewayDTO> findAll() {
		List<StorePaymentGateway> list = repository.findAll();
		List<StorePaymentGatewayDTO> listDto = mapper.convertToDtoList(list);
		return listDto.isEmpty()?null:listDto;
	}

	@Override
	public StorePaymentGateway save(StorePaymentGatewayDTO dto) {
		StorePaymentGateway payment = mapper.convertToEntity(dto);
		payment = repository.save(payment);
		return payment;
	}

}