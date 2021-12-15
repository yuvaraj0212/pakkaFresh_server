package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.CartItemsModel;

public interface CartItemsRepo extends JpaRepository<CartItemsModel, Long>{

	@Query(value="select cart.* from tbl_cart_items cart where cart.user_id=?1", nativeQuery = true)
	List<CartItemsModel> findByUserModel(Long id);

}
