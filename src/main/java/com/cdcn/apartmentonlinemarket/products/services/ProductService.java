package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;
import java.util.UUID;

import com.cdcn.apartmentonlinemarket.helpers.response.PageResponse;
import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SearchProductRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
	List<ProductDTO> getAllProducts();
	ProductDTO save(ProductDTO productDto, MultipartFile[] files);
	PageResponse<ProductDTO> filter(SearchProductRequest request);
	ProductDTO getOne(UUID productId);
	List<ProductDTO> getAllByOwner(UUID storeId);
}