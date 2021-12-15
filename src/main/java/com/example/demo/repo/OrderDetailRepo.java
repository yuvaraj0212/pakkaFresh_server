package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderDetails;
@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetails, Long> {

	@Query(nativeQuery = true,value = "select * from tbl_order_details where user_id=?1 ")
	List<OrderDetails> findByUserModel(Long id);

}
