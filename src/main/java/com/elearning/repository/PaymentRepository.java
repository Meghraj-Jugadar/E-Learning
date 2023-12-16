package com.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elearning.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
