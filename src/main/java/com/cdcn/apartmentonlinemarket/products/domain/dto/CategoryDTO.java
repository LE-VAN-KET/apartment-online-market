package com.cdcn.apartmentonlinemarket.products.domain.dto;

import java.util.List;
import java.util.UUID;

public class CategoryDTO {
	private Long id;
	private String name;
	private String description;
	private Long parent_id;
	private String slug;
	private List<UUID> products_id;
	private Integer status;

	public Long getId() {
		return id;
	}

	
	public CategoryDTO(Long id, String name, String description, Long parent_id,String slug, List<UUID> products_id,Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parent_id = parent_id;
		this.slug =slug;
		this.products_id = products_id;
		this.status = status;
	}


	public CategoryDTO() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public List<UUID> getProducts_id() {
		return products_id;
	}

	public void setProducts_id(List<UUID> products_id) {
		this.products_id = products_id;
	}


	public String getSlug() {
		return slug;
	}


	public void setSlug(String slug) {
		this.slug = slug;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

}
