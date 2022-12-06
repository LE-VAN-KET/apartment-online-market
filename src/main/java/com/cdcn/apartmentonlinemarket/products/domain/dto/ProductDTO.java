package com.cdcn.apartmentonlinemarket.products.domain.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class ProductDTO {
	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer quantity;
	private Integer limitPrioty;
	private Time saleDate;
	private String status;
	private UUID store_id;
	private Long category_id;
	private List<Long> tag_ids;

	 public ProductDTO() {
		super();
	}

	public ProductDTO(UUID id, String name, String description, BigDecimal price, Integer quantity, Integer limitPrioty,
			Time saleDate, String status, UUID store_id, Long category_id, List<Long> tag_ids) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.limitPrioty = limitPrioty;
		this.saleDate = saleDate;
		this.status = status;
		this.store_id = store_id;
		this.category_id = category_id;
		this.tag_ids = tag_ids;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getLimitPrioty() {
		return limitPrioty;
	}

	public void setLimitPrioty(Integer limitPrioty) {
		this.limitPrioty = limitPrioty;
	}

	public Time getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Time saleDate) {
		this.saleDate = saleDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UUID getStore_id() {
		return store_id;
	}

	public void setStore_id(UUID store_id) {
		this.store_id = store_id;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public List<Long> getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(List<Long> tag_ids) {
		this.tag_ids = tag_ids;
	}



}