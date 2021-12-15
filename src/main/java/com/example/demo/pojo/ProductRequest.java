package com.example.demo.pojo;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

	private Long productId;
	@NotEmpty(message="product name must not to be null")
	private String productName;
	@NotEmpty(message="product code must not to be null")
	private String productCode;
	private String description;
	@NotEmpty(message="product details must not to be null")
	private String details;
	private String discount;
	@NotEmpty(message="product price must not to be null")
	private String price;
	private MultipartFile mfile;
	@NotEmpty(message="product category must not to be null")
	private String category;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public MultipartFile getMfile() {
		return mfile;
	}

	public void setMfile(MultipartFile mfile) {
		this.mfile = mfile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
