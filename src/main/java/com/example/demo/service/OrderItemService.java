package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.OrderItems;
import com.example.demo.model.ProductModel;
import com.example.demo.model.UserModel;
import com.example.demo.pojo.OrderDetailsRequest;
import com.example.demo.repo.OrderDetailRepo;
import com.example.demo.repo.OrderItemsRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.UserRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Service
public class OrderItemService extends ExceptionController{
	
	@Autowired
	OrderDetailRepo orderRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	OrderItemsRepo itemRepo;
	
	 public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
	    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");


	public ResponseEntity<Object> createOrder(OrderDetailsRequest orderDetails){
		OrderDetails order = new OrderDetails();
		
		Set<OrderItems> listOrderItem = new HashSet<>();
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserModel user = userRepo.findByEmail(userDetails.getEmail());
		if(user == null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"user details not available");
		}
		List<Object> list = orderDetails.getProduct();
		for (Object object : list) {
			OrderItems orderItem = new OrderItems();
			Map<String,String> obj = (Map<String, String>) object;
			ProductModel product = productRepo.findById(Long.parseLong(obj.get("productId"))).get();
			orderItem.setCreatedDate(new Date());
			orderItem.setModifiedDate(new Date());
			orderItem.setProductModel(product);
			orderItem.setQuantity(obj.get("quantity"));
			listOrderItem.add(orderItem);
		}
		itemRepo.saveAll(listOrderItem);
		order.setUserModel(user);
		order.setOrders(listOrderItem);
		order.setOrderDate(new Date());
		order.setAmount(orderDetails.getAmount());
		order.setOrderStatus("ordered");
		orderRepo.save(order);
//		 Twilio.init("AC5f109f8be7a37d49bd2ec57591eedf41", "4413a36bdaba20bb0a441ceeb5883588");
//	        Message message = Message.creator(
//	        		 new PhoneNumber("+917639348128"),
//	        		    new PhoneNumber("+17793452885"),
//	                "pandiyan stores online purchase message demo")
//	            .create();
//
//	        System.out.println(message.getSid());
		return response(HttpStatus.OK.value(),"ordered successfully",order);
	}
	
	
	public ResponseEntity<Object> orderList(){
		List<OrderDetails> orderList = orderRepo.findAll();
		return response(HttpStatus.OK.value(),"order list",orderList);
	}
	
	
	
	public ResponseEntity<Object> userOrderList(){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserModel user = userRepo.findByEmail(userDetails.getEmail());
		List<OrderDetails> orderList = orderRepo.findByUserModel(user.getId());
		return response(orderList);
	}
}
