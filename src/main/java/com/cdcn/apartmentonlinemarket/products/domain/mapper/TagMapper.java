package com.cdcn.apartmentonlinemarket.products.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cdcn.apartmentonlinemarket.infrastructure.domain.mapper.BaseMapper;
import com.cdcn.apartmentonlinemarket.products.domain.dto.TagDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Product;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Tag;

@Component
public class TagMapper extends BaseMapper<Tag, TagDTO> {



	@Override
	public Tag convertToEntity(TagDTO dto, Object... args) {
		Tag tag = new Tag();
		tag.setDescription(dto.getColor());
		tag.setName(dto.getName());
		tag.setPosition(dto.getPosition());
		return tag;
	}

	@Override
	public TagDTO convertToDto(Tag entity, Object... args) {
		TagDTO dto = new TagDTO();
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPosition(entity.getPosition());
		if (entity.getColor()!=null) {
			dto.setColor(entity.getColor().getCode());
		}
		if (entity.getProducts()!=null) {
			List<UUID> productIds =new ArrayList<>();
			for (Product item : entity.getProducts()) {
				productIds.add(item.getId());
			}
			dto.setProducts_id(productIds);
		}
		return dto;
	}

}