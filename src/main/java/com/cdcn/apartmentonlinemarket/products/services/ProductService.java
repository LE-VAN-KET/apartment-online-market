package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;

import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;

public interface ProductService {
	List<ProductDTO> getAllProducts();
	ProductDTO save(ProductDTO product_dto);
}
