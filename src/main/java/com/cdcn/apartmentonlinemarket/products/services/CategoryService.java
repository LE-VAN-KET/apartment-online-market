package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;

import com.cdcn.apartmentonlinemarket.products.domain.dto.CategoryDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;

public interface CategoryService {
	
	List<CategoryDTO> getAllCategory();
	Category getCategoryById(Long id);
	CategoryDTO save(CategoryDTO dto);
	

}
