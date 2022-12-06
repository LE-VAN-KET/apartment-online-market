package com.cdcn.apartmentonlinemarket.products.services.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.domain.mapper.ProductMapper;
import com.cdcn.apartmentonlinemarket.products.repository.ProductRepository;
import com.cdcn.apartmentonlinemarket.products.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> products = new ArrayList<>();
		products = productMapper.convertToDtoList(productRepository.findAll());
	    return products.isEmpty()?null:products;
	}

	@Override
	public ProductDTO save(ProductDTO productDto) {
		Product product = productMapper.convertToEntity(productDto);
		product = productRepository.save(product);
		return productMapper.convertToDto(product);
	}

}