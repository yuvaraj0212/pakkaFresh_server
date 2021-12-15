package com.example.demo.model;
import java.util.Date;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_orderitems")
public class OrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "order_id", nullable = false,referencedColumnName = "order_id")
//	private OrderDetails orderDetails;
//	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false,referencedColumnName = "id")
	private ProductModel productModel;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "order_id", nullable = false,referencedColumnName = "order_id")
//	private OrderDetails orderDetails;
	private String quantity;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProductModel getProductModel() {
		return productModel;
	}
	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}
//	public OrderDetails getOrderDetails() {
//		return orderDetails;
//	}
//	public void setOrderDetails(OrderDetails orderDetails) {
//		this.orderDetails = orderDetails;
//	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public OrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
