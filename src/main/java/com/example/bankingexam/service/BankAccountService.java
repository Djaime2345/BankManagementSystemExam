package com.example.bankingexam.service;

import com.example.bankingexam.exception.GlobalExceptionHandler;
import com.example.bankingexam.exception.InsufficientFundsException;
import com.example.bankingexam.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for Bank account
 */
@Service
public class BankAccountService {
    private final Map<Long, BankAccount> accounts = new HashMap<>();
    private long currentId = 1;

    /**
     * Constructor for the Bank Account Class
     *
     * @param accountHolder that must not be blank
     * @param initialBalance that must be greater than 0
     * @return The bank account created
     */
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
        return bankAccount;
    }

    /**
     * Method that deposit an amount of money to an account
     *
     * @param accountId where the money will be deposited. Must exist in the system
     * @param amount must be greater than 0
     */
    public void deposit(long accountId, double amount) {
        // Check if account exits
        try {
            BankAccount optionalBankAccount = this.getAccount(accountId);

            // Check if amount is valid
            if (amount > 0){
                optionalBankAccount.setBalance(optionalBankAccount.getBalance() + amount);
            } else {
                throw new RuntimeException("The amount must be positive in order to deposit it");
            }
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }

    }

    /**
     * Method that withdraw an amount of money from an account
     *
     * @param accountId where the money will be withdrawn. Must exist in the system
     * @param amount must be greater than 0
     * @throws InsufficientFundsException if there is not enough money to withdraw
     */
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

    /**
     * Method that return an account of the system
     *
     * @param accountId ID of the account
     * @return The account for that ID
     */
    public BankAccount getAccount(long accountId) {
        BankAccount optionalBankAccount = accounts.get(accountId);

        // Check if the account exits in the system
        if (optionalBankAccount != null){
            return optionalBankAccount;
        } else {
            throw new NullPointerException("No account find with that id");
        }
    }
}
