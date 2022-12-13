package com.cdcn.apartmentonlinemarket.products.controller;

import java.util.List;
import java.util.UUID;

import com.cdcn.apartmentonlinemarket.helpers.response.PageResponse;
import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SearchProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.services.ProductService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public List<ProductDTO> GetAllProduct() {
		List<ProductDTO> products = productService.getAllProducts();
		return products;
	}

	@PostMapping(value = "/products", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ProductDTO CreateProduct(@RequestPart ProductDTO product, @RequestPart("files") MultipartFile[] files) {
		ProductDTO p = productService.save(product, files);
		return p;
	}

	@GetMapping("/products/filter")
	public PageResponse<ProductDTO> filter(SearchProductRequest request) {
		return productService.filter(request);
	}

	@GetMapping("products/{id}")
	public ProductDTO getDetailsProduct(@PathVariable("id") String productId) {
		return productService.getOne(UUID.fromString(productId));
	}

}