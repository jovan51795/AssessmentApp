package com.assessment.service;

import com.assessment.dto.CustomerDto;
import com.assessment.dto.CustomerRequestDto;
import com.assessment.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface CustomerService {
    Long create(CustomerDto accountDto);

    Optional<ApiResponse> getCustomer(String customerNumber, CustomerRequestDto customerRequestDto) throws JsonProcessingException;
}
