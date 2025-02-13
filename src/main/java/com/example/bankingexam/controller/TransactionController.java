package com.example.bankingexam.controller;

import com.example.bankingexam.model.Transaction;
import com.example.bankingexam.model.TransactionType;
import com.example.bankingexam.service.BankAccountService;
import com.example.bankingexam.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
/**
 * REST API Controller for transactions
 */
public class TransactionController {
    private final TransactionService transactionService;
    private final BankAccountService accountService;

    /**
     * Default Constructor
     *
     * @param accountService
     */
    public TransactionController(BankAccountService accountService) {
        this(null, accountService);
    }

    /**
     * Constructor with Service Provided
     *
     * @param transactionService
     * @param accountService
     */
    public TransactionController(TransactionService transactionService, BankAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    /**
     * Endpoint for record a deposit in the system
     *
     * @param accountId ID of the account to record the transaction
     * @param amount Money to be deposited
     * @return Message for the user
     */
    @PostMapping("/record/deposit")
    public String recordDeposit(@RequestParam long accountId, @RequestParam double amount) {
        this.transactionService.recordTransaction(accountId, amount, TransactionType.DEPOSIT);
        return "Deposit Recorded";
    }

    /**
     * Endpoint for record a withdrawn in the system
     *
     * @param accountId ID of the account to record the transaction
     * @param amount Money to be deposited
     * @return Message for the user
     */
    @PostMapping("/record/withdraw")
    public String recordWithdraw(@RequestParam long accountId, @RequestParam double amount) {
        this.transactionService.recordTransaction(accountId, amount, TransactionType.WITHDRAWAL);
        return "Withdrawn Recorded";
    }

    /**
     * Endpoint for the retrieve of the transactions of a bank account
     *
     * @param accountId ID of the bank account
     * @return List of transactions
     */
    @GetMapping("/{accountId}")
    public List<Transaction> getTransactions(@PathVariable long accountId) {
        return this.getTransactions(accountId);
    }
}
