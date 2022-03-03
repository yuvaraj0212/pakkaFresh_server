package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.webpageModel;
@Repository
public interface websetingsRepo extends JpaRepository<webpageModel, Long> {

	Object findAllById(int i);

}
