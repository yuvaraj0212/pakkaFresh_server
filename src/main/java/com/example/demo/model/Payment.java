package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.util.PaymentStatus;


@Entity
@Table(name = "payment")

public class Payment {
	 @Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column
	    private String email;
	    @Column
	    private String name;
	    @Column
	    private String phone;
	    @Column
	    private Double amount;
	    @Column
	    @Enumerated(EnumType.STRING)
	    private PaymentStatus paymentStatus;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(PaymentStatus paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
		public Payment() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Payment(Long id, String email, String name, String phone, Double amount, PaymentStatus paymentStatus) {
			super();
			this.id = id;
			this.email = email;
			this.name = name;
			this.phone = phone;
			this.amount = amount;
			this.paymentStatus = paymentStatus;
		}
	
}
