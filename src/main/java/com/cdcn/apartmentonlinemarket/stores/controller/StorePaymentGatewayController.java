package com.cdcn.apartmentonlinemarket.stores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StorePaymentGatewayDTO;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.StorePaymentGateway;
import com.cdcn.apartmentonlinemarket.stores.service.StorePaymentGatewayService;

@RestController
@RequestMapping("/api")
public class StorePaymentGatewayController {

	@Autowired
	private StorePaymentGatewayService service;

	@GetMapping("/payment-gateway")
	public List<StorePaymentGatewayDTO> GetAllStores() {
		List<StorePaymentGatewayDTO> payments = service.findAll();
		return payments;
	}

	@PostMapping("/payment-gateway")
	public StorePaymentGateway CreatePaymentGateway(@RequestBody StorePaymentGatewayDTO dto) {
		System.out.println("controller");
		StorePaymentGateway p = service.save(dto);
		return p;
	}

}