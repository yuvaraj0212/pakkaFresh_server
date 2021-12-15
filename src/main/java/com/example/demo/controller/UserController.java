package com.example.demo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.UserAddressModel;
import com.example.demo.model.UserModel;
import com.example.demo.pojo.LoginRequest;
import com.example.demo.pojo.ResetPassword;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@RestController
public class UserController extends ExceptionController {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserService userService;
	
	@PostMapping(value="/signin")
	public Object authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.signin(loginRequest);
	}
	
	@PostMapping(value = "/signup")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserModel userModel){
		return userService.signUp(userModel);
	}	
	
	@GetMapping(value="/user-list")
	public ResponseEntity<Object> getUserList(){
		return userService.getUserList();
	}
	
	@GetMapping(value="/current-user")
	public ResponseEntity<Object> getCurrentUser(){
		return userService.getCurrentUser();
	}
	
	@PostMapping(value="/update-user")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel userModel){
		return userService.updateUser(userModel);
	}
	
	@GetMapping(value="/forget-password")
	public ResponseEntity<Object> forgotPassword(@RequestParam(name="emailId")String emailId,String role){
		return userService.forgotPassword(emailId,role);
	}
	
	@PostMapping(value="/reset-password/{emailId}")
	public ResponseEntity<Object> resetPassword(@PathVariable("emailId")String emailId,@RequestBody @Valid ResetPassword resetPassword){
		return userService.resetPassword(emailId,resetPassword);
	}
	
	@PostMapping("/add-address")
	public ResponseEntity<Object> addUserAddress(@Valid @RequestBody UserAddressModel model){
		return userService.userAddressCreate(model);
	}
	
	@GetMapping("/user-address")
	public ResponseEntity<Object> getUserAddress(){
		return userService.getUserAddress();
	}

}
