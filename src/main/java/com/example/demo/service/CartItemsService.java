package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.CartItemsModel;
import com.example.demo.model.ProductModel;
import com.example.demo.model.UserModel;
import com.example.demo.pojo.CartItemsRequest;
import com.example.demo.repo.CartItemsRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.UserRepo;

@Service
public class CartItemsService extends ExceptionController {

	@Autowired
	UserRepo userRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	CartItemsRepo cartRepo;

	public ResponseEntity<Object> addCart(CartItemsRequest request) {
		UserModel user = userRepo.findById(request.getUserId()).get();
		ProductModel product = productRepo.findById(request.getProductId()).get();
		if (user == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"user details not available");
		}
		if (product == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"product details not available");
		}
		CartItemsModel cartDetails = new CartItemsModel();
		cartDetails.setUserModel(user);
		cartDetails.setProductModel(product);
		cartDetails.setQuantity(request.getQuantity());
		cartDetails.setCreatedDate(new Date());
		cartDetails.setModifiedDate(new Date());
		cartRepo.save(cartDetails);
		return response(HttpStatus.OK.value(), "cart details updated", cartDetails);
	}
	
	public ResponseEntity<Object> updateCart(CartItemsRequest request){
		if(request.getId() == null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"cart id must not to be null");
		}
		CartItemsModel cart = cartRepo.findById(request.getId()).orElseThrow(() -> new RuntimeException("cart details not available"));
		cart.setModifiedDate(new Date());
		cart.setQuantity(request.getQuantity());
		cartRepo.save(cart);
		return response(HttpStatus.OK.value(),"cart updated successfully",cart);
	}
	
	public ResponseEntity<Object> getUserCartList(){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<CartItemsModel> cartList = cartRepo.findByUserModel(userDetails.getId());
		return response(HttpStatus.OK.value(),"user cart details",cartList);
	}
	
	public ResponseEntity<Object> deleteUserCart(Long cartId){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if(cartId == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"cart id must not to be null");
		}
		CartItemsModel cart = cartRepo.findById(cartId).get();
		if(cart == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"please provide valid cart id");
		}
		cartRepo.delete(cart);
		return response(HttpStatus.OK.value(),"cart removed successfully");
	}
}
