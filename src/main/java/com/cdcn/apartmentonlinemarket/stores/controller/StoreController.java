package com.cdcn.apartmentonlinemarket.stores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDTO;
import com.cdcn.apartmentonlinemarket.stores.service.StoreService;

@RestController
public class StoreController {
	@Autowired
	private StoreService service;

	@GetMapping("/stores")
	public List<StoreDTO> GetAllStores() {
		List<StoreDTO> stores = service.findAll();
		return stores;
	}

	@PostMapping("/stores")
	public StoreDTO CreateStore(@RequestBody StoreDTO dto) {
		System.out.println("controller");
		StoreDTO p = service.save(dto);
		return p;
	}
}