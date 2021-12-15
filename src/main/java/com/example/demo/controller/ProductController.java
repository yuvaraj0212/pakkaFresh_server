package com.example.demo.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.ExceptionController;
import com.example.demo.model.ProductModel;
import com.example.demo.pojo.ProductRequest;
import com.example.demo.repo.ProductRepo;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController extends ExceptionController {

	@Autowired
	ProductRepo productRepo;
	@Autowired
	ProductService productService;
	

	@PostMapping(value = "/create-product")
	public ResponseEntity<Object> createProduct(@Valid ProductRequest productRequest) throws Exception {
		return productService.createProduct(productRequest);
	}

	@PostMapping(value = "/update-product")
	public ResponseEntity<Object> editProduct(@Valid ProductRequest productRequest) throws IOException {
		return productService.updateProduct(productRequest);
	}

	@GetMapping(value = "/product-list")
	public ResponseEntity<Object> productDetails(){
		return productService.getProductList();
	}

	@DeleteMapping(value = "/delete-product")
	public ResponseEntity<Object> deleteProduct(@RequestParam(name = "productId") Long productId) {
		return productService.deleteProduct(productId);
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return productService.downloadFile(fileName, request);
    }
	
	@GetMapping("/product-details")
	public ResponseEntity<Object> getProductDetails(@RequestParam("productId")Long productId){
		return productService.getProductDetails(productId);
	}
	
	@GetMapping("/category-filter")
	public ResponseEntity<Object> getProductByCategory(@RequestParam("categoryId")Long categoryId){
		return productService.getProductByCategory(categoryId);
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<Object> pagination(@RequestParam(name = "pageNo") int offset,@RequestParam (name = "pageSize") int pageSize) {
		return productService.pagination(offset, pageSize);
	}

}
