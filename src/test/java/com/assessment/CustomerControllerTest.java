package com.assessment;

import com.assessment.controller.CustomerController;
import com.assessment.dto.CustomerDto;
import com.assessment.dto.CustomerRequestDto;
import com.assessment.enums.AccountType;
import com.assessment.response.ApiResponse;
import com.assessment.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureWebTestClient
public class CustomerControllerTest {
    @MockBean
    CustomerService customerService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturn409WhenCustomerAlreadyExists() {
        CustomerDto request = getRequest();

        when(customerService.create(any())).thenReturn(null);

        webTestClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectBody()
                .jsonPath("$.transactionStatusCode")
                .isEqualTo(409);
        verify(customerService, times(1)).create(any());
    }

    @Test
    void customerCreationSuccessful() {
        CustomerDto request = getRequest();

        when(customerService.create(any())).thenReturn(1L);

        webTestClient.post()
                .uri("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectBody()
                .jsonPath("$.transactionStatusCode")
                .isEqualTo(201);
        verify(customerService, times(1)).create(any());
    }

    @Test
    void getCustomer_NotFound() throws JsonProcessingException {
        CustomerRequestDto customerRequestDto = getCustomerRequestDto();

        when(customerService.getCustomer(anyString(), any())).thenReturn(Optional.empty());
        webTestClient.method(HttpMethod.GET)
                .uri("/api/v1/account/1")
                .bodyValue(customerRequestDto)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody()
                .jsonPath("$.transactionStatusCode")
                .isEqualTo(401);

        verify(customerService, times(1)).getCustomer(anyString(), any());

    }

    @Test
    void getCustomer_BadRequest() throws JsonProcessingException {

        when(customerService.getCustomer(anyString(), any())).thenReturn(Optional.empty());

        webTestClient.get()
                .uri("/api/v1/account/1"
                )
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getCustomer_Success() throws JsonProcessingException {
        CustomerRequestDto customerRequestDto = getCustomerRequestDto();

        when(customerService.getCustomer(anyString(), any())).thenReturn(Optional.of(new ApiResponse(HttpStatus.FOUND.value(), "Customer account found")));
        webTestClient.method(HttpMethod.GET)
                .uri("/api/v1/account/1")
                .bodyValue(customerRequestDto)
                .exchange()
                .expectStatus()
                .isFound()
                .expectBody()
                .jsonPath("$.transactionStatusCode")
                .isEqualTo(302);

        verify(customerService, times(1)).getCustomer(anyString(), any());

    }

    public static CustomerRequestDto getCustomerRequestDto() {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setCustomerNumber(1L);
        customerRequestDto.setCustomerEmail(getRequest().getCustomerEmail());
        customerRequestDto.setCustomerMobile(getRequest().getCustomerMobile());
        customerRequestDto.setAccountType(getRequest().getAccountType());
        customerRequestDto.setCustomerName(getRequest().getCustomerName());
        customerRequestDto.setAddress1(getRequest().getAddress1());
        customerRequestDto.setAddress2(getRequest().getAddress2());
        return customerRequestDto;
    }

    public static CustomerDto getRequest() {
        CustomerDto request = new CustomerDto();
        request.setCustomerName("benji");
        request.setCustomerMobile("9123456789");
        request.setCustomerEmail("benji@gmail.com");
        request.setAccountType(AccountType.S);
        request.setAddress1("test1");
        request.setAddress2("test2");
        return  request;
    }
}
