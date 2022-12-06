package com.cdcn.apartmentonlinemarket.products.domain.dto;

import java.util.List;
import java.util.UUID;

public class TagDTO {
	private Long id;
	private String name;
	private String description;
	private Integer position;
	private String color;
	private List<UUID> products_id;
	public TagDTO() {
		super();
	}
	public Long getId() {
		return id;
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
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<UUID> getProducts_id() {
		return products_id;
	}
	public void setProducts_id(List<UUID> products_id) {
		this.products_id = products_id;
	}


}