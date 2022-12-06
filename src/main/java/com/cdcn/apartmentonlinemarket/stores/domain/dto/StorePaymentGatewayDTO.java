package com.cdcn.apartmentonlinemarket.stores.domain.dto;

import java.util.UUID;

public class StorePaymentGatewayDTO {
	private UUID id;
	private String gatewayName;
	private Boolean enable;
	private String logo;
	private Integer position;
	private UUID storeId;

	public StorePaymentGatewayDTO() {
		super();
	}

	public StorePaymentGatewayDTO(UUID id, String gatewayName, Boolean enable, String logo, Integer position) {
		super();
		this.id = id;
		this.gatewayName = gatewayName;
		this.enable = enable;
		this.logo = logo;
		this.position = position;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public UUID getstoreId() {
		return storeId;
	}

	public void setstoreId(UUID storeId) {
		this.storeId = storeId;
	}
}