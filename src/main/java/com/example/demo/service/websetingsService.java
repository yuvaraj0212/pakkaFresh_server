package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.UserModel;
import com.example.demo.model.promoCodes;
import com.example.demo.model.webpageModel;
import com.example.demo.pojo.ProductRequest;
import com.example.demo.repo.promoCodeRepo;
import com.example.demo.repo.websetingsRepo;

@Service
public class websetingsService  extends ExceptionController {

	@Autowired
	websetingsRepo webRepo;
	@Autowired
	promoCodeRepo pCodeRepo;
	@Autowired
	FileStorageService fileStorageService;

	public  ResponseEntity<Object> updateweb(webpageModel web) {
//		webpageModel obj = new webpageModel();
		webpageModel  obj = webRepo.findById(web.getId()).get();
		obj.setPromo(web.getPromo());
		webRepo.save(obj);
		return response(HttpStatus.OK.value(), "webpage updated Succcessfully", obj);
	}

	public ResponseEntity<Object> getweb() {
		long id = 0;
		webpageModel  obj = webRepo.findById(id).get();
		return response(obj);
	}

	public ResponseEntity<Object> createPcode(promoCodes pcode) {
		promoCodes obj = new promoCodes();
		obj.setpCode(pcode.getpCode());
		obj.setFilename(pcode.getMfile().getOriginalFilename());
		obj.setImageURL(fileStorageService.uploadFile(pcode.getMfile(),"banner"));
		pCodeRepo.save(obj);
		return  response(obj);
	}

	public ResponseEntity<Object> getPcode() {
		List<promoCodes> PromoCodes = pCodeRepo.findAll();
		return response(HttpStatus.OK.value(), "PromoCodes", PromoCodes);
	}

	public ResponseEntity<Object> deletePcode(promoCodes pcode) {
//		promoCodes  obj = pCodeRepo.findById(pcode.getId()).get();
		pCodeRepo.deleteById(pcode.getId());
		return response(HttpStatus.OK.value(), "promoCode delete Succcessfully");
	}

}
