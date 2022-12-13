package com.cdcn.apartmentonlinemarket.products.services.impl;


import java.util.ArrayList;
import java.util.List;

import com.cdcn.apartmentonlinemarket.helpers.pagination.PageRequestBuilder;
import com.cdcn.apartmentonlinemarket.helpers.specs.FilterSpecifications;
import com.cdcn.apartmentonlinemarket.helpers.response.PageResponse;
import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SearchProductRequest;
import com.cdcn.apartmentonlinemarket.products.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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

	@Autowired
	private InventoryService inventoryService;

	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> products = new ArrayList<>();
		try {
			products = productMapper.convertToDtoList(productRepository.findAll());
		}
		catch (Exception e) {
		}
	    return products.isEmpty()?null:products;
	}

	@Override
	public ProductDTO save(ProductDTO productDto) {
		Product product = productMapper.convertToEntity(productDto);
		product = productRepository.save(product);
		addProductToInventory(product);
		return productMapper.convertToDto(product);
	}

	@Override
	public PageResponse<ProductDTO> filter(SearchProductRequest request) {
		Pageable pageable = PageRequestBuilder.getPageable(request.getSize(),
				request.getPage(), request.getSorts());
		FilterSpecifications<Product> productSearchSpecifications = new FilterSpecifications<>();
		productSearchSpecifications.addCondition(request.filterCriteriaList());
		Page<Product> page = productRepository.findAll(productSearchSpecifications, pageable);
		PageResponse<ProductDTO> response = new PageResponse<>();
		List<ProductDTO> productDTOList = productMapper.convertToDtoList(page.getContent());
		response.setPageStats(page, productDTOList);
		return response;
	}

	@Async
	public void addProductToInventory(Product product) {
		inventoryService.add(product);
	}

}