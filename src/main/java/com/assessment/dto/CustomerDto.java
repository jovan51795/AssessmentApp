package com.assessment.dto;

import com.assessment.enums.AccountType;
import com.assessment.model.Customer;
import com.assessment.model.Savings;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class CustomerDto {
    @NotBlank(message = "Customer Name is Required")
    private String customerName;

    @Min(value = 10, message = "Invalid Mobile Number")
    @Pattern(regexp = "9\\d{9}", message = "Invalid number format. Must Start with 9")
    private String customerMobile;

    @NotBlank(message = "Customer Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String customerEmail;

    @NotBlank(message =  "Address1 is Required")
    private String address1;

    @NotBlank(message =  "Address2 is Required")
    private String address2;

    @NotNull(message = "Account type is Required")
    private AccountType accountType;

    public Customer toCustomer() {
        int randomNumber = 10000000 + new Random().nextInt(90000000);
        Customer customer = new Customer();
        customer.setCustomerNumber(String.valueOf(randomNumber));
        customer.setCustomerEmail(this.getCustomerEmail());
        customer.setCustomerMobile(this.getCustomerMobile());
        customer.setCustomerName(this.getCustomerName());
        customer.setAddress1(this.getAddress1());
        customer.setAddress2(this.getAddress2());
        customer.setAccountType(this.getAccountType());

        Double availableBal = 1000000000 + new Random().nextDouble();
        Savings savings = new Savings(randomNumber + "", getAccountType().getType(), availableBal);
        savings.setCustomer(customer);
        List<Savings> savingsList = new ArrayList<>();
        savingsList.add(savings);
        customer.setSavings(savingsList);
        return customer;
    }
}
