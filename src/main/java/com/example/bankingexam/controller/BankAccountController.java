package com.example.bankingexam.controller;

import com.example.bankingexam.dto.DepositRequest;
import com.example.bankingexam.dto.WithdrawRequest;
import com.example.bankingexam.exception.InsufficientFundsException;
import com.example.bankingexam.model.BankAccount;
import com.example.bankingexam.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
/**
 * REST API Controller for Bank Account
 */
public class BankAccountController {
    private final BankAccountService accountService;

    /**
     * Default Constructor
     */
    public BankAccountController() {
        this(null);
    }

    /**
     * Constructor with service provided
     *
     * @param accountService
     */
    public BankAccountController(BankAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Endpoint for create a new account
     *
     * @param accountHolder Name of the holder
     * @param initialBalance Initial amount of money
     * @return Bank account created
     */
    @PostMapping("/create")
    public BankAccount createAccount(@RequestParam String accountHolder, @RequestParam double initialBalance) {
        return this.accountService.createAccount(accountHolder, initialBalance);
    }

    /**
     * Endpoint for get an account
     *
     * @param id ID of the account
     * @return Bank account with that ID
     */
    @GetMapping("/{id}")
    public BankAccount getAccount(@PathVariable long id) {
        return this.accountService.getAccount(id);
    }

    /**
     * Endpoint for make deposits in an account
     *
     * @param request DTO of the deposit request (Check dto/DepositRequest for more info)
     */
    @PatchMapping("/deposit")
    public void deposit(@Valid @RequestBody DepositRequest request) {
        this.accountService.deposit(request.getAccountId(), request.getAmount());
    }

    /**
     * Endpoint for make withdraws in an account
     *
     * @param request DTO of the deposit withdraw (Check dto/WithdrawRequest for more info)
     * @throws InsufficientFundsException if not enough money in the balance to withdraw
     */
    @PatchMapping("/withdraw")
    public void withdraw(@Valid @RequestBody WithdrawRequest request) throws InsufficientFundsException {
        try{
            this.accountService.withdraw(request.getAccountId(), request.getAmount());
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
    }
}
