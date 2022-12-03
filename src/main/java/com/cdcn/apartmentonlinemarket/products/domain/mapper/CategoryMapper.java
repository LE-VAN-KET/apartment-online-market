package com.cdcn.apartmentonlinemarket.products.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.products.domain.dto.CategoryDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.repository.CategoryRepository;

@Component
public class CategoryMapper extends BaseMapper<Category, CategoryDTO> {
	
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public CategoryDTO convertToDto(Category entity, Object... args) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(entity.getId());
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		if (entity.getParent()!=null) {
			dto.setParent_id(entity.getParent().getId());
		}
		if (entity.getProducts()!=null) {
			List<UUID> products_id = new ArrayList<UUID>();
			for (Product item : entity.getProducts()) {
				products_id.add(item.getId());
			}
			dto.setProducts_id(products_id);
		}
		dto.setSlug(entity.getSlug());
		if (entity.getStatus()!=null) {
			dto.setStatus(Integer.valueOf(entity.getStatus().toString()));
		}
		return dto;
		
	}

	@Override
	public Category convertToEntity(CategoryDTO dto, Object... args) {
		Category category = new Category();
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		category.setSlug(dto.getSlug());
		if (dto.getParent_id()!=null) {
			Optional<Category> category_parent = repository.findById(dto.getParent_id());
			if (!category_parent.isEmpty()) {
				category.setParent(category_parent.get());
			}
		}
		
		return category;
	}
}
