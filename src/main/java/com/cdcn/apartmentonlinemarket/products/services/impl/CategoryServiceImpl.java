package com.cdcn.apartmentonlinemarket.products.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.products.domain.dto.CategoryDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;
import com.cdcn.apartmentonlinemarket.products.domain.mapper.CategoryMapper;
import com.cdcn.apartmentonlinemarket.products.repository.CategoryRepository;
import com.cdcn.apartmentonlinemarket.products.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryMapper mapper;

	@Override
	public Category getCategoryById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.orElse(null);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> categoryList = categoryRepository.findAll();

		return categoryList.isEmpty() ? Collections.emptyList() : mapper.convertToDtoList(categoryList);

	}

	@Override
	public CategoryDTO save(CategoryDTO dto) {
		Category category = new Category();
		try {
			category = mapper.convertToEntity(dto);
			category = categoryRepository.save(category);
		}
		catch (Exception e) {	
		}
		return mapper.convertToDto(category);
	}

}