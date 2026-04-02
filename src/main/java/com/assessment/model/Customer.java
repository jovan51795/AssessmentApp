package com.assessment.model;

import com.assessment.dto.CustomerRequestDto;
import com.assessment.enums.AccountType;
import com.assessment.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerNumber;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Savings> savings;

    public ApiResponse toCustomerRequestDto() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String customerStr = mapper.writeValueAsString(this);

        return mapper.readValue(customerStr, ApiResponse.class);
    }

}
