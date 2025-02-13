package com.example.bankingexam.controller;

import com.example.bankingexam.model.Transaction;
import com.example.bankingexam.model.TransactionType;
import com.example.bankingexam.service.BankAccountService;
import com.example.bankingexam.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final BankAccountService accountService;


    public TransactionController(BankAccountService accountService) {
        this(null, accountService);
    }

    public TransactionController(TransactionService transactionService, BankAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    // Annotation with ("/record/deposit") url
    @PostMapping("/record/deposit")
    public String recordDeposit(@RequestParam long accountId, @RequestParam double amount) {
        // Implement logic here
        this.transactionService.recordTransaction(accountId, amount, TransactionType.DEPOSIT);
        // Temporary return, you should replace it with the appropriate value according to the method's logic.
        return null;
    }

    // Annotation with ("/record/withdraw") url
    @PostMapping("/record/withdraw")
    public String recordWithdraw(@RequestParam long accountId, @RequestParam double amount) {
        // Implement logic here
        this.transactionService.recordTransaction(accountId, amount, TransactionType.WITHDRAWAL);
        return null; // Temporary return, you should replace it with the appropriate value according to the method's logic.

    }

    // Annotation with ("/{accountId}") url
    @GetMapping("/{accountId}")
    public List<Transaction> getTransactions(@PathVariable long accountId) {
        // Implement logic here
        return this.getTransactions(accountId);// Temporary return, you should replace it with the appropriate value according to the method's logic.
    }
}
