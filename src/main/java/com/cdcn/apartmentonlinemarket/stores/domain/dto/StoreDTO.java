package com.cdcn.apartmentonlinemarket.stores.domain.dto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StoreDTO {
	private UUID id;
	private String name;
	private String description;
	private UUID ownerId;
	private Date establishDate;
	private BigDecimal star;
	private String status;
	private List<UUID> productIds;
	private List<UUID> paymentGatewayIds;

	public StoreDTO() {
		super();
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

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public Date getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}

	public BigDecimal getStar() {
		return star;
	}

	public void setStar(BigDecimal star) {
		this.star = star;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<UUID> getproductIds() {
		return productIds;
	}

	public void setproductIds(List<UUID> productIds) {
		this.productIds = productIds;
	}

	public List<UUID> getpaymentGatewayIds() {
		return paymentGatewayIds;
	}

	public void setpaymentGatewayIds(List<UUID> paymentGatewayIds) {
		this.paymentGatewayIds = paymentGatewayIds;
	}
}