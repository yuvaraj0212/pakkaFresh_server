package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CategoryModel;

public interface CategoryRepo extends JpaRepository<CategoryModel, Long> {

	CategoryModel findByName(String category);

	boolean existsByName(String name);

	List<CategoryModel> findByStatus(boolean status);

}
