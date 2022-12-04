package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.domain.mapper.ProductMapper;
import com.cdcn.apartmentonlinemarket.products.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<ProductDTO> getAllProducts() {
		try {
			List<ProductDTO> products = productMapper.convertToDtoList(productRepository.findAll());
		    return products.isEmpty()?null:products;
		}
		catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public ProductDTO save(ProductDTO product_dto) {
		Product product = productMapper.convertToEntity(product_dto);
		product = productRepository.save(product);
		return productMapper.convertToDto(product);
	}

}
