package com.cdcn.apartmentonlinemarket.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.services.ProductService;

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

	@PostMapping("/products")
	public ProductDTO CreateProduct(@RequestBody ProductDTO product) {
		ProductDTO p = productService.save(product);
		return p;
	}

}