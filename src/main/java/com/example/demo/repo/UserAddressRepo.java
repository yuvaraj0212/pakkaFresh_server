package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.UserAddressModel;
import com.example.demo.model.UserModel;

public interface UserAddressRepo extends JpaRepository<UserAddressModel, Long>{

	@Query(nativeQuery = true,value = "SELECT * FROM tbl_user_address where user_id=?1 ")
	List<UserAddressModel> findAddress(Long id);

}
