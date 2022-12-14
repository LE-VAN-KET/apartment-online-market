package com.cdcn.apartmentonlinemarket.products.services.impl;


import java.util.*;

import com.cdcn.apartmentonlinemarket.exception.ProductNotEnoughException;
import com.cdcn.apartmentonlinemarket.helpers.pagination.PageRequestBuilder;
import com.cdcn.apartmentonlinemarket.helpers.specs.FilterSpecifications;
import com.cdcn.apartmentonlinemarket.helpers.response.PageResponse;
import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SearchProductRequest;
import com.cdcn.apartmentonlinemarket.products.services.FilesStorageService;
import com.cdcn.apartmentonlinemarket.products.services.InventoryService;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private FilesStorageService filesStorageService;

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> productList = productRepository.findAll();
	    return productList.isEmpty() ? Collections.emptyList() :
				productMapper.convertToDtoList(productList);
	}

	@Override
	@Transactional
	public ProductDTO save(ProductDTO productDto, MultipartFile[] files) {
		Product product = productMapper.convertToEntity(productDto);
		List<String> fileNames = new ArrayList<>();
		Arrays.asList(files).stream().forEach(file -> {
			filesStorageService.save(file);
			fileNames.add(file.getOriginalFilename());
		});
		String images = String.join(";", fileNames);
		product.setImages(images);
		product = productRepository.save(product);
		addProductToInventory(product);
		return productMapper.convertToDto(product);
	}

	@Override
	public PageResponse<ProductDTO> filter(SearchProductRequest request) {
		Pageable pageable = PageRequestBuilder.getPageable(request.getSize(),
				request.getPage(), request.getSorts());
		FilterSpecifications<Product> productSearchSpecifications =
				new FilterSpecifications<>(request.getFilterCriteriaList());
		Page<Product> page = productRepository.findAll(productSearchSpecifications, pageable);
		PageResponse<ProductDTO> response = new PageResponse<>();
		List<ProductDTO> productDTOList = productMapper.convertToDtoList(page.getContent());
		response.setPageStats(page, productDTOList);
		return response;
	}

	@Override
	public ProductDTO getOne(UUID productId) {
		Product product = productRepository.findById(productId).orElseThrow(() ->
				new ProductNotEnoughException(404, "Product not found with id!"));
		return productMapper.convertToDto(product);
	}

	@Async
	public void addProductToInventory(Product product) {
		inventoryService.add(product);
	}

}