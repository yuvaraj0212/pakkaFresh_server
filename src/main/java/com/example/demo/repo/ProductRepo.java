package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ProductModel;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel, Long> {

	boolean existsByCode(String productCode);

	boolean existsByName(String productName);

	@Query(value="select * from tbl_products where cate_id=?1",nativeQuery=true)
	List<ProductModel> findByCategory(Long categoryId);

	List<ProductModel> findByStatus(boolean status);

}
