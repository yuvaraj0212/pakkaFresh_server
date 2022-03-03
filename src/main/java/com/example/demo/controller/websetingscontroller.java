package com.example.demo.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.promoCodes;
import com.example.demo.model.webpageModel;
import com.example.demo.pojo.ProductRequest;
import com.example.demo.service.websetingsService;

@RestController
public class websetingscontroller extends ExceptionController{
		
	@Autowired
	websetingsService websetingsService;
	@PostMapping(value = "/update-web")
	public ResponseEntity<Object> updateweb(@RequestBody webpageModel web) throws IOException {
		return websetingsService.updateweb(web);
	}

	@GetMapping(value = "/get-web")
	public ResponseEntity<Object> updateweb(){
		return websetingsService.getweb();
	}
	
	@PostMapping(value = "/create-pcode")
	public ResponseEntity<Object> createPcode(promoCodes pcode) throws IOException {
		return websetingsService.createPcode(pcode);
	}
	
	@GetMapping(value = "/get-pcode")
	public ResponseEntity<Object> getPcode(){
		return websetingsService.getPcode();
	}
	
	@PostMapping(value = "/delete-pcode")
	public ResponseEntity<Object> deletePcode(@RequestBody promoCodes pcode) throws IOException {
		return websetingsService.deletePcode(pcode);
	}
	
}
