package com.assessment.service.impl;

import com.assessment.dto.CustomerDto;
import com.assessment.dto.CustomerRequestDto;
import com.assessment.model.Customer;
import com.assessment.repository.CustomerRepository;
import com.assessment.response.ApiResponse;
import com.assessment.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Long create(CustomerDto accountDto) {

        Optional<Customer> customer = customerRepository.findByCustomerEmailAndCustomerMobile(accountDto.getCustomerEmail(), accountDto.getCustomerMobile());
        if(customer.isPresent()) {
            return null;
        }
        return customerRepository.save(accountDto.toCustomer()).getId();
    }


    @Override
    public Optional<ApiResponse> getCustomer(String customerNumber, CustomerRequestDto customerRequestDto ) throws JsonProcessingException {

        Optional<Customer> customer = customerRepository.findByCustomerNumberAndCustomerEmail(customerNumber, customerRequestDto.getCustomerEmail());
        if(customer.isPresent()) {
            return Optional.of(customer.get().toCustomerRequestDto());
        }
       return Optional.empty();

    }
}
