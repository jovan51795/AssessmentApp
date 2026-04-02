package com.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequestDto extends CustomerDto{
    private Long customerNumber;
    private List<SavingsDto> savings;

}
