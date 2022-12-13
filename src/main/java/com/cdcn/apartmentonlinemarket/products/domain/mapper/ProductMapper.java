package com.cdcn.apartmentonlinemarket.products.domain.mapper;
import java.util.*;

import com.cdcn.apartmentonlinemarket.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.products.domain.dto.ProductDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Category;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Tag;
import com.cdcn.apartmentonlinemarket.products.repository.CategoryRepository;
import com.cdcn.apartmentonlinemarket.products.repository.TagRepository;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.repository.StoreRepository;

@Component
public class ProductMapper extends BaseMapper<Product, ProductDTO>{

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Override
	public ProductDTO convertToDto(Product entity, Object... args) {
		ProductDTO dto = new ProductDTO();
		if (entity.getCategory()!=null){
			dto.setCategory_id(entity.getCategory().getId());
		}
		if (entity.getStore()!=null) {
			dto.setStore_id(entity.getStore().getId());
		}
		if (entity.getTags()!=null) {
			List<Long> tags_id = new ArrayList<Long>();
			for (Tag tag :entity.getTags()) {
				tags_id.add(tag.getId());
			}
			dto.setTag_ids(tags_id);
		}
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setLimitPrioty(entity.getLimitPrioty());
		dto.setName(entity.getName());
		dto.setPrice(entity.getPrice());
		dto.setQuantity(entity.getQuantity());

		if (entity.getImages() != null && !entity.getImages().isEmpty()) {
			dto.setImagesList(Arrays.asList(entity.getImages().split(";")));
		}

		if (entity.getSaleDate()!=null) {
			dto.setSaleDate(entity.getSaleDate());
		}
		if (entity.getStatus()!=null) {
			dto.setStatus(entity.getStatus().toString());
		}
		return dto;
	}

	@Override
	public Product convertToEntity(ProductDTO dto, Object... args) {
		Product p = new Product();
		p.setLimitPrioty(dto.getLimitPrioty());
		p.setDescription(dto.getDescription());
		p.setName(dto.getName());
		p.setPrice(dto.getPrice());
		p.setQuantity(dto.getQuantity());
		p.setSaleDate(dto.getSaleDate());
		if (dto.getCategory_id()!=null) {
			Category category = categoryRepository.findById(dto.getCategory_id()).orElseThrow(() ->
					new CategoryNotFoundException(404, "Category not found with id!"));
			p.setCategory(category);
		}
		if (dto.getStore_id()!=null) {
			Optional<Store> store = storeRepository.findById(dto.getStore_id());
			store.ifPresent(p::setStore);
		}
		if (dto.getTag_ids()!=null) {
			List<Tag> tags = tagRepository.findAllById(dto.getTag_ids());
			Set<Tag> tagSet = new HashSet<>(tags);
			p.setTags(tagSet);
		}
		return p;
	}

}