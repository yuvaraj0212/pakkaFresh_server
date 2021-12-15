package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.Role;
import com.example.demo.model.UserAddressModel;
import com.example.demo.model.UserModel;
import com.example.demo.pojo.LoginRequest;
import com.example.demo.pojo.ResetPassword;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserAddressRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.util.JwtUtils;

@Service
public class UserService extends ExceptionController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	UserRepo userRepo;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	FileStorageService fileStorageService;
	@Autowired
	UserAddressRepo addressRepo;
	

	public ResponseEntity<Object> signin(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		UserModel user = userRepo.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("user not found"));
		user.setToken(jwt);
		return response(HttpStatus.OK.value(), user);
	}

	public ResponseEntity<Object> signUp(UserModel userModel) {
		UserModel userDetails = new UserModel();
		boolean emailExists = userRepo.existsByEmail(userModel.getEmail());
		if (emailExists) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Email ID already exists");
		}
		if(!userModel.getPassword().equals(userModel.getConfirmPassword())) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"password and confirm password not matched please check");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode = encoder.encode(userModel.getPassword());
		userDetails.setPassword(encode);
		userDetails.setUsername(userModel.getUsername());
		userDetails.setConfirmPassword(encode);
		userDetails.setPhone(userModel.getPhone());
		userDetails.setEmail(userModel.getEmail());
		userDetails.setCreatedDate(new Date());
		userDetails.setModifiedDate(new Date());
		Set<Role> role = roleRepo.findByRolename("user");
		userDetails.setRoles(role);
		userRepo.save(userDetails);
		mailSender(userDetails.getEmail(),"Welcome to shoping world buddy");
		return response(HttpStatus.OK.value(), "user created successfully", userDetails);
	}

	public void mailSender(String emailId,String subject) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("shaping <kprabha10192@gamil.com>");
		email.setSubject(subject);
		email.setText("");
		email.setTo(emailId);
		mailSender.send(email);
	}

	public ResponseEntity<Object> getUserList() {
//		List<UserModel> userList = userRepo.findByRole("user");
		List<UserModel> userList = userRepo.findAll();
		return response(HttpStatus.OK.value(), "userlist", userList);
	}

	public ResponseEntity<Object> getCurrentUser() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserModel user = userRepo.findByEmail(userDetails.getEmail());
		return response(HttpStatus.OK.value(), "user details", user);
	}

	public ResponseEntity<Object> updateUser(UserModel userModel) {
		UserModel userDetails = userRepo.findById(userModel.getId()).get();
		if (userDetails == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"User details are not avalaible");
		}
		String fileName = null;
		if(userModel.getMfile() != null ) {
			try {
				fileName = fileStorageService.storeFile(userModel.getMfile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/downloadFile/")
					.path(fileName).toUriString();
			userDetails.setFilename(userModel.getMfile().getOriginalFilename());
			userDetails.setImageURL(fileDownloadUri);
		}

//		userDetails.setAddress(userModel.getAddress());
		userDetails.setPhone(userModel.getPhone());
		userDetails.setUsername(userModel.getUsername());
		userDetails.setModifiedDate(new Date());
		userRepo.save(userDetails);
		return response(HttpStatus.OK.value(), "user details updated successfully", userDetails);
	}
	
	public ResponseEntity<Object> forgotPassword(String emailId ,String role){
		UserModel userDetails = userRepo.findByEmail(emailId);
		if(userDetails == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"User details are not avalaible");
		}
//		mailSender(userDetails.getEmail(),"forgot password link");
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("shaping <kprabha10192@gamil.com>");
		email.setSubject("forgot password link");
		if(role == "admin") {
		email.setText("http://3.110.127.217/account/resetpassword/"+emailId);
		}else {
			email.setText("http://3.110.127.217/account/resetpassword/"+emailId);
		}
		email.setTo(emailId);
		System.out.println(email);
		mailSender.send(email);
		
		return response(HttpStatus.OK.value(),"reset password link has been to sent to your registered mail");
	}
	
	public ResponseEntity<Object> resetPassword(String emailId,ResetPassword resetPassword){
		UserModel userDetails = userRepo.findByEmail(emailId);
		if(userDetails == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"User details are not avalaible");
		}
		if(!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"password and confirm password not matched please check");
		}
		
		BCryptPasswordEncoder cryptPassword = new BCryptPasswordEncoder();
		userDetails.setPassword(cryptPassword.encode(resetPassword.getPassword()));
		userDetails.setConfirmPassword(cryptPassword.encode(resetPassword.getConfirmPassword()));
		userRepo.save(userDetails);
		return response(HttpStatus.OK.value(),"Password updated successfully");
	}
	
	public ResponseEntity<Object> userAddressCreate(UserAddressModel addressModel){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserModel user = userRepo.findByEmail(userDetails.getEmail());
		addressModel.setUserModel(user);
		addressModel.setCreatedDate(new Date());
		addressModel.setModifiedDate(new Date());
		addressRepo.save(addressModel);
		return response(HttpStatus.OK.value(),"address added successfully");
	}
	
	public ResponseEntity<Object> updateAddress(UserAddressModel addressModel){
		UserAddressModel address = addressRepo.findById(addressModel.getId()).get();
		address.setStreet(addressModel.getStreet());
		address.setCity(addressModel.getCity());
		address.setDistrict(addressModel.getDistrict());
		address.setState(addressModel.getState());
		address.setPincode(addressModel.getPincode());
		address.setModifiedDate(new Date());
		addressRepo.save(address);
		return response(HttpStatus.OK.value(),"address updated successfully");
	}
	
	public ResponseEntity<Object> getAddress(Long addressId){
		UserAddressModel address = addressRepo.findById(addressId).get();
		if(address == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"User address details are not avalaible");
		}
		return response(HttpStatus.OK.value(),"user address details", address);
	}
	
	public ResponseEntity<Object> getUserAddress(){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<UserAddressModel> address = addressRepo.findAddress(userDetails.getId());
		return response(HttpStatus.OK.value(),"user address details", address);
	}
	
}
