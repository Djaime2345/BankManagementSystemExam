package com.example.bankingexam.service;

import com.example.bankingexam.exception.GlobalExceptionHandler;
import com.example.bankingexam.exception.InsufficientFundsException;
import com.example.bankingexam.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BankAccountService {
    private final Map<Long, BankAccount> accounts = new HashMap<>();
    private long currentId = 1;

    public BankAccount createAccount(String accountHolder, double initialBalance) {
        // Check if balance is correct
        if (initialBalance < 0){
            throw new RuntimeException("Balance must be positive");
        }

        // Check if name is correct
        if (accountHolder.equalsIgnoreCase("")){
            throw new RuntimeException("Name must not be empty");
        }

        // Create the bank account with the parameters passed
        BankAccount bankAccount = new BankAccount(this.currentId, accountHolder, initialBalance);
        accounts.put(this.currentId, bankAccount);
        this.currentId++;
        return bankAccount; // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }

    public void deposit(long accountId, double amount) {
        // Implement logic here
        try {
            BankAccount optionalBankAccount = this.getAccount(accountId);
            if (amount > 0){
                optionalBankAccount.setBalance(optionalBankAccount.getBalance() + amount);
            } else {
                throw new RuntimeException("The amount must be positive in order to deposit it");
            }
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }

    }

    public void withdraw(long accountId, double amount) throws InsufficientFundsException {
        // Check if account exits
        try {
            BankAccount optionalBankAccount = this.getAccount(accountId);
            // Check if the amount is valid

            if (amount <= 0){
                throw new RuntimeException("Amount must be greater than 0");
            }

            if (amount <= optionalBankAccount.getBalance()) {
                optionalBankAccount.setBalance(optionalBankAccount.getBalance() - amount);
            } else {
                throw new InsufficientFundsException("Insufficient balance");
            }
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public BankAccount getAccount(long accountId) {
        // Implement logic here
        BankAccount optionalBankAccount = accounts.get(accountId);

        if (optionalBankAccount != null){
            return optionalBankAccount;
        } else {
            throw new NullPointerException("No account find with that id");
        }
         // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }

    public void checkAmount(double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Amount must be positive");
        }
    }
}
