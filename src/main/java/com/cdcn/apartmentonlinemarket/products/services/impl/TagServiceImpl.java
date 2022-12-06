package com.cdcn.apartmentonlinemarket.products.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdcn.apartmentonlinemarket.products.domain.dto.TagDTO;
import com.cdcn.apartmentonlinemarket.products.domain.entity.Tag;
import com.cdcn.apartmentonlinemarket.products.domain.mapper.TagMapper;
import com.cdcn.apartmentonlinemarket.products.repository.TagRepository;
import com.cdcn.apartmentonlinemarket.products.services.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagMapper tagMapper;

	@Override
	public List<Tag> getAllTagByIds(List<Long> ids) {
		List<Tag> tags = tagRepository.findAllById(ids);
		return tags.isEmpty() ? null : tags;
	}

	@Override
	public List<TagDTO> findAll() {
		return tagMapper.convertToDtoList(tagRepository.findAll());
	}

	@Override
	public TagDTO save(TagDTO dto) {
		Tag tag = tagRepository.save(tagMapper.convertToEntity(dto));
		return tagMapper.convertToDto(tag);
	}

}