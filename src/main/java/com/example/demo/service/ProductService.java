package com.example.demo.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.CategoryModel;
import com.example.demo.model.ProductModel;
import com.example.demo.pojo.ProductRequest;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductRepo;

@Service
public class ProductService extends ExceptionController {

	@Autowired
	ProductRepo productRepo;
	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	FileStorageService fileStorageService;

	public ResponseEntity<Object> createProduct(ProductRequest productRequest) {
		boolean productNameExists = productRepo.existsByName(productRequest.getProductName());
		boolean productCodeExists = productRepo.existsByCode(productRequest.getProductCode());
		if (productNameExists || productCodeExists) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"product name/code already exists");
		} else if (productRequest.getMfile() == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"File must not empty");
		}

		CategoryModel categoryModel = categoryRepo.findByName(productRequest.getCategory());
		if (categoryModel == null) {
			return failure(HttpStatus.OK.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"please check the category type");
		}
		ProductModel productModel = new ProductModel(productRequest);
		productModel.setFilename(productRequest.getMfile().getOriginalFilename());
		productModel.setImageURL(fileStorageService.uploadFile(productRequest.getMfile(),"product"));
		productModel.setCategory(categoryModel);
		productModel.setCreateDate(new Date());
		productModel.setModifiedDate(new Date());
		productRepo.save(productModel);
		return response(HttpStatus.OK.value(), "product added Succcessfully", productModel);
	}

	public ResponseEntity<Object> updateProduct(ProductRequest productRequest) {
		
		if (productRequest.getProductId() == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"product id must not to be null");
		}
		ProductModel editProduct = productRepo.findById(productRequest.getProductId()).get();
		if(editProduct == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"product details not available");
		}
		if ((productRequest.getMfile()) == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"File must not empty");
		}
		CategoryModel categoryModel = categoryRepo.findByName(productRequest.getCategory());
		if (categoryModel == null) {
			return failure(HttpStatus.OK.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"please check the category type");
		}
		String fileName = null;
		try {
			fileName = fileStorageService.storeFile(productRequest.getMfile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		editProduct.setFilename(productRequest.getMfile().getOriginalFilename());
		editProduct.setImageURL(fileStorageService.uploadFile(productRequest.getMfile(),"product"));
		editProduct.setName(productRequest.getProductName());
		editProduct.setDiscount(productRequest.getDiscount());
		editProduct.setPrice(productRequest.getPrice());
		editProduct.setDetails(productRequest.getDetails());
		editProduct.setCode(productRequest.getProductCode());
		editProduct.setModifiedDate(new Date());
		editProduct.setCategory(categoryModel);
		editProduct.setDescription(productRequest.getDescription());
		productRepo.save(editProduct);
		return response(HttpStatus.OK.value(), "updated Succcessfully", editProduct);
	}

	public ResponseEntity<Object> getProductList() {
		List<ProductModel> productList = productRepo.findByStatus(true);
		Collections.reverse(productList);
		return response(HttpStatus.OK.value(), "product list", productList);
	}

	public ResponseEntity<Object> deleteProduct(Long productId) {
		ProductModel productDetail = productRepo.findById(productId).get();
		if (productDetail == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Please check the product details");
		}
		productDetail.setStatus(false);
		productRepo.save(productDetail);
		return response(HttpStatus.OK.value(), "product deleted successfully");
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

	public ResponseEntity<Object> getProductDetails(Long productId) {
		ProductModel productDetail = productRepo.findById(productId).get();
		if (productDetail == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Please check the product details");
		}
		return response(HttpStatus.OK.value(), "product details", productDetail);
	}
	
	public ResponseEntity<Object> getProductByCategory(Long categoryId){
		if(categoryId == null) {
			return failure(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"category must not to be null");
		}
		List<ProductModel> productList = productRepo.findByCategory(categoryId);
		return response(HttpStatus.OK.value(),"product list",productList);
	}
	
	public ResponseEntity<Object> pagination(Integer offset, Integer pageSize) {
		 Page<ProductModel> paging = productRepo.findAll(PageRequest.of(offset, pageSize));
		 return response(HttpStatus.OK.value(),"pagination",paging);
	}
}
