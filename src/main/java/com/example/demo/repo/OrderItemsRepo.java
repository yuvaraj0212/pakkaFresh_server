package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderItems;


@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {
//	
//	@Query("SELECT coalesce(max(u.id),0 )FROM Order u")
//	String findById();  

}
