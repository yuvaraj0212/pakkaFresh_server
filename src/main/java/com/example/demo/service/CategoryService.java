package com.example.demo.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.CategoryModel;
import com.example.demo.repo.CategoryRepo;


@Service
public class CategoryService extends ExceptionController{

	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	FileStorageService fileStorageService;
	
	public ResponseEntity<Object> createCategory(CategoryModel categoryModel){
		boolean model = categoryRepo.existsByName(categoryModel.getName());
		if(model) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category type already exists");
		}
		if(categoryModel.getMfile() == null ) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category image must not to be null");
		}
//		try {
//			fileName = fileStorageService.storeFile(categoryModel.getMfile());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/downloadFile/")
//				.path(fileName).toUriString();
		categoryModel.setFileName(categoryModel.getMfile().getOriginalFilename());
		categoryModel.setImageURL(fileStorageService.uploadFile(categoryModel.getMfile(), "category"));
		categoryModel.setMfile(null);
		categoryModel.setCreatedDate(new Date());
		categoryModel.setModifiedDate(new Date());
		categoryRepo.save(categoryModel);
		return response(HttpStatus.OK.value(),"category created successfully",categoryModel);
	}
	
	public ResponseEntity<Object> updateCategory(CategoryModel categoryModel){
		if(categoryModel.getId()== null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category id must not to be null");
		}
		CategoryModel model = categoryRepo.findById(categoryModel.getId()).get();
		if(model == null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category details not available");
		}
		String fileName = null;
		if(categoryModel.getMfile() == null ) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category image must not to be null");
		}
//		try {
//			fileName = fileStorageService.storeFile(categoryModel.getMfile());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/downloadFile/")
//				.path(fileName).toUriString();
		model.setFileName(categoryModel.getMfile().getOriginalFilename());
		model.setImageURL(fileStorageService.uploadFile(categoryModel.getMfile(), "category"));
		model.setMfile(null);
		model.setName(categoryModel.getName());
		model.setDesc(categoryModel.getDesc());
		model.setModifiedDate(new Date());
		categoryRepo.save(model);
		return response(HttpStatus.OK.value(),"category updated successfully",model);
	}
	
	public ResponseEntity<Object> getCategory(Long categoryId){
		CategoryModel categoryModel = categoryRepo.findById(categoryId).get();
		if(categoryModel == null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category details not available");
		}
		return response(HttpStatus.OK.value(),"category details",categoryModel);
	}
	
	public ResponseEntity<Object> deleteCategory(Long categoryId){
		CategoryModel categoryModel = categoryRepo.findById(categoryId).get();
		if(categoryModel == null) {
			return failure(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"category details not available");
		}
		categoryModel.setStatus(false);
		categoryRepo.save(categoryModel);
		return response(HttpStatus.OK.value(),"category deleted successfully");
	}
	
	public ResponseEntity<Object> categoryList(){
		List<CategoryModel> categoryModel = categoryRepo.findByStatus(true);
		Collections.reverse(categoryModel);
		return response(HttpStatus.OK.value(),"category list",categoryModel);
	}
	
	public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) throws Exception {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
//			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
