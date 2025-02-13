package com.example.bankingexam.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DepositRequest {
    @NotNull
    private Long accountId;

    @Positive
    private double amount;

    public Long getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }
}
