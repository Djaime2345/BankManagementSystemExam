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
public class BankAccountController {
    private final BankAccountService accountService;

    public BankAccountController() {
        this(null);
    }

    public BankAccountController(BankAccountService accountService) {
        this.accountService = accountService;
    }

    // Annotation with ("/create") url
    @PostMapping("/create")
    public BankAccount createAccount(@RequestParam String accountHolder, @RequestParam double initialBalance) {
        // Implement logic here
        return this.accountService.createAccount(accountHolder, initialBalance);
        // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }

    // Annotation with ("/{id}") url
    @GetMapping("/{id}")
    public BankAccount getAccount(@PathVariable long id) {
        // Implement logic here
        return this.accountService.getAccount(id);
        // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }

    // Annotation with ("/deposit") url
    @PatchMapping("/deposit")
    public void deposit(@Valid @RequestBody DepositRequest request) {
        // Implement logic here
        this.accountService.deposit(request.getAccountId(), request.getAmount());
    }

    // Annotation with ("/withdraw") url
    @PatchMapping("/withdraw")
    public void withdraw(@Valid @RequestBody WithdrawRequest request) throws InsufficientFundsException {
        // Implement logic here
        try{
            this.accountService.withdraw(request.getAccountId(), request.getAmount());
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
    }
}
