package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {

	UserModel findByEmail(String email);

	boolean existsByEmail(String email);

	@Query(value = "select users.* from tbl_user users, users_roles ur, tbl_role roles "
			+ "where roles.rolename =:role and roles.id= ur.role_id and ur.user_id = users.id", nativeQuery = true)
	List<UserModel> findByRole(@Param("role") String userRole);

}
