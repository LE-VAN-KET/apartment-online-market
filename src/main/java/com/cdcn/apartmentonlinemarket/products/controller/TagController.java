package com.cdcn.apartmentonlinemarket.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdcn.apartmentonlinemarket.products.domain.dto.TagDTO;
import com.cdcn.apartmentonlinemarket.products.services.TagService;

@RestController
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/tags")
	public List<TagDTO> GetAllTag() {
		List<TagDTO> tags = tagService.findAll();
		return tags;
	}
	
	@PostMapping("/tags")
	public TagDTO save(@RequestBody TagDTO dto) {
		TagDTO tag = tagService.save(dto);
		return tag;
	}
}
