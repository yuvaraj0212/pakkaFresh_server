package com.example.demo.pojo;

import javax.validation.constraints.NotNull;

public class CartItemsRequest {

	private Long id;
	@NotNull(message="user must not to be null")
	private Long userId;
	@NotNull(message="product must not to be null")
	private Long productId;
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
