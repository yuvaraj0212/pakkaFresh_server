package com.example.demo.pojo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderDetailsRequest {

	private Long id;
	@NotNull(message="user must not to be null")
	private List<Object> product;
	@NotNull(message="amount must not to be null")
	private double amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public double getAmount() {
		return amount;
	}
	public List<Object> getProduct() {
		return product;
	}
	public void setProduct(List<Object> product) {
		this.product = product;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	

}
