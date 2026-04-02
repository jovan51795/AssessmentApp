package com.assessment;


import com.assessment.enums.AccountType;
import com.assessment.model.Customer;
import com.assessment.repository.CustomerRepository;
import com.assessment.response.ApiResponse;
import com.assessment.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static com.assessment.CustomerControllerTest.getCustomerRequestDto;
import static com.assessment.CustomerControllerTest.getRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @MockBean
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_AlreadyExist() {
        Customer existingCustomer = new Customer();
        when(customerRepository.findByCustomerEmailAndCustomerMobile(anyString(), anyString()))
                .thenReturn(Optional.of(existingCustomer));
        Long id = customerService.create(getRequest());
        assertNull(id);
        verify(customerRepository, times(1)).findByCustomerEmailAndCustomerMobile(anyString(), anyString());
    }

    @Test
    void createCustomer_Success() {
        Customer customer = getCustomerData();

        when(customerRepository.findByCustomerEmailAndCustomerMobile(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);
        Long id = customerService.create(getRequest());
        assertNotNull(id);
        assertEquals(id, 1L);
        verify(customerRepository, times(1)).findByCustomerEmailAndCustomerMobile(anyString(), anyString());
    }

    @Test
    void getCustomer_NotFound() throws JsonProcessingException {
        when(customerRepository.findByCustomerNumberAndCustomerEmail(anyString(), anyString())).thenReturn(Optional.empty());

        Optional<ApiResponse> response = customerService.getCustomer("1", getCustomerRequestDto());

        assertFalse(response.isPresent());

        verify(customerRepository, times(1)).findByCustomerNumberAndCustomerEmail(anyString(), anyString());
    }

    @Test
    void getCustomer() throws JsonProcessingException {
        when(customerRepository.findByCustomerNumberAndCustomerEmail(anyString(), anyString())).thenReturn(Optional.of(getCustomerData()));

        Optional<ApiResponse> response = customerService.getCustomer("1", getCustomerRequestDto());

        assertNotNull(response.get());

        verify(customerRepository, times(1)).findByCustomerNumberAndCustomerEmail(anyString(), anyString());
    }

    private Customer getCustomerData() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("Benji");
        customer.setCustomerMobile("9123456789");
        customer.setCustomerEmail("benji@example.com");
        customer.setAddress1("123 Main Street");
        customer.setAddress2("Apartment 4B");
        customer.setAccountType(AccountType.S);
        customer.setSavings(new ArrayList<>());

        return customer;
    }
}
