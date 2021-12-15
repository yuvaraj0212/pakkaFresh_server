package com.example.demo.controller;

import javax.validation.Valid;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OrderDetails;
import com.example.demo.pojo.OrderDetailsRequest;
import com.example.demo.repo.OrderDetailRepo;
import com.example.demo.service.OrderItemService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@RestController
public class OrderDetailController  {
	
	@Autowired
	OrderItemService service;
	
	@PostMapping("/order")
	public ResponseEntity<Object> createOrderDetail(@Valid @RequestBody OrderDetailsRequest orderDetail) {
		return service.createOrder(orderDetail);
	}
	
	@GetMapping("/order-list")
	public ResponseEntity<Object> orderList() {
		return service.orderList();
	}

	@GetMapping("/user-orderlist")
	public ResponseEntity<Object> getUserOrderList() {
		return service.userOrderList();
	}
}
