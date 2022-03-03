package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.promoCodes;

@Repository
public interface promoCodeRepo extends JpaRepository<promoCodes, Long> {

}
