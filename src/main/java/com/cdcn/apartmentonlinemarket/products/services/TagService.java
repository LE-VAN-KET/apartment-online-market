package com.cdcn.apartmentonlinemarket.products.services;

import java.util.List;

import com.cdcn.apartmentonlinemarket.products.domain.dto.TagDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Tag;

public interface TagService {

	List<Tag> getAllTagByIds(List<Long> ids);
	List<TagDTO> findAll();
	TagDTO save(TagDTO dto);
}