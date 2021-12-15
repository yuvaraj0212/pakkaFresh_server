package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.CartItemsRequest;
import com.example.demo.service.CartItemsService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartItemsService cartService;

	@PostMapping(value = "/add-cart")
	public ResponseEntity<Object> addCart(@Valid @RequestBody CartItemsRequest request) {
		return cartService.addCart(request);
	}
	
	@PostMapping("/update-cart")
	public ResponseEntity<Object> updateCart(@Valid @RequestBody CartItemsRequest request){
		return cartService.updateCart(request);
	}
	
	@GetMapping("/user-cart")
	public ResponseEntity<Object> getUserCartList(){
		return cartService.getUserCartList();
	}
	
	@DeleteMapping("/delete-cart")
	public ResponseEntity<Object> deleteUserCart(@RequestParam(value="cartId")Long cartId){
		return cartService.deleteUserCart(cartId);
	}
	
	@DeleteMapping("/cartbulk-delete")
	public ResponseEntity<Object> deleteBulkData(){
		return null;
	}
}
