package com.assessment.repository;

import com.assessment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerNumberAndCustomerEmail(String customerNumber, String email);

    Optional<Customer> findByCustomerEmailAndCustomerMobile(String customerEmail, String customerMobile);

}
