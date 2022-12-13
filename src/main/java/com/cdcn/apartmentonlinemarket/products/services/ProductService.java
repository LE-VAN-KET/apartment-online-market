package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;

import com.cdcn.apartmentonlinemarket.helpers.response.PageResponse;
import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SearchProductRequest;

public interface ProductService {
	List<ProductDTO> getAllProducts();
	ProductDTO save(ProductDTO productDto);
	PageResponse<ProductDTO> filter(SearchProductRequest request);
}