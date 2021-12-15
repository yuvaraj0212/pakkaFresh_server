package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Payment;
@Repository
public interface PaymentRepo extends JpaRepository<Payment, String> {

}
