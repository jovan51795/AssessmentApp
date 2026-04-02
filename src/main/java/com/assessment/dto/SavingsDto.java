package com.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsDto {
    private String accountNumber;
    private String accountType;
    private Double availableBalance;
}
