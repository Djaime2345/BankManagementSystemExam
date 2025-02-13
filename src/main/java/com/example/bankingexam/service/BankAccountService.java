package com.example.bankingexam.service;

import com.example.bankingexam.exception.InsufficientFundsException;
import com.example.bankingexam.model.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountService {
    private final Map<Long, BankAccount> accounts = new HashMap<>();
    private long currentId = 1;

    public BankAccount createAccount(String accountHolder, double initialBalance) {
        // Implement logic here
        return null; // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }

    public void deposit(long accountId, double amount) {
        // Implement logic here
    }

    public void withdraw(long accountId, double amount) throws InsufficientFundsException {
        // Implement logic here
    }

    public BankAccount getAccount(long accountId) {
        // Implement logic here
        return null; // Temporary return, you should replace it with the appropriate value according to the method's logic.

    }
}
