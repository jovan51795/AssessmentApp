package com.assessment.response;

import com.assessment.dto.CustomerRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse extends CustomerRequestDto {
    private int transactionStatusCode;
    private String transactionStatusDescription;

    public ApiResponse(int code, String description) {
        this.transactionStatusCode = code;
        this.transactionStatusDescription = description;
    }

    public ApiResponse(int code, String description, Long customerNumber) {
        this.transactionStatusCode = code;
        this.transactionStatusDescription = description;
        this.setCustomerNumber( customerNumber);
    }
}
