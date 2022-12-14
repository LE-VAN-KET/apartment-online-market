package com.cdcn.apartmentonlinemarket.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdcn.apartmentonlinemarket.products.domain.dto.CategoryDTO;
import com.cdcn.apartmentonlinemarket.products.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public List<CategoryDTO> GetAllCategory() {
		List<CategoryDTO> categories = categoryService.getAllCategory();
		return categories;
	}

	@PostMapping("/categories")
	public CategoryDTO save(@RequestBody CategoryDTO dto) {
		CategoryDTO category = categoryService.save(dto);
		return category;
	}

}