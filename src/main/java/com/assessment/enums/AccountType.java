package com.assessment.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    Y("Y"),
    S("Savings"),
    C("Checking");

    private final String type;

    AccountType(String type) {
        this.type = type;
    }
}
