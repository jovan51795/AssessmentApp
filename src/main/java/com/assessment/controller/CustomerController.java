package com.assessment.controller;

import com.assessment.dto.CustomerDto;
import com.assessment.dto.CustomerRequestDto;
import com.assessment.response.ApiResponse;
import com.assessment.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CustomerDto customerDto) {
        Long id = customerService.create(customerDto);
        ApiResponse response;
        if(id == null) {
            response = new ApiResponse(  HttpStatus.CONFLICT.value(), "Customer account already exist");
            return new ResponseEntity<>( response, HttpStatus.CONFLICT);
        }
        response = new ApiResponse(  HttpStatus.CREATED.value(), "Customer account created", id);
        return new ResponseEntity<>( response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<ApiResponse> getCustomer(@PathVariable String customerNumber, @RequestBody CustomerRequestDto requestDto) throws JsonProcessingException {

        return customerService.getCustomer(customerNumber, requestDto)
                .map(apiResponse -> {
                    apiResponse.setTransactionStatusCode(HttpStatus.FOUND.value());
                    apiResponse.setTransactionStatusDescription("Customer account found");
                    return new ResponseEntity<>( apiResponse, HttpStatus.FOUND);
                }).orElseGet(() -> {
                    ApiResponse apiFailedResponse = new ApiResponse( HttpStatus.UNAUTHORIZED.value(), "Customer not found");
                    return new ResponseEntity<>(apiFailedResponse, HttpStatus.UNAUTHORIZED);
                });
    }
}
