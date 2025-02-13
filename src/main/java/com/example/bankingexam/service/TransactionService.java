package com.example.bankingexam.service;

import com.example.bankingexam.model.Transaction;
import com.example.bankingexam.model.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Comparator;

public class TransactionService {
    private final ConcurrentHashMap<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();
    private long currentTransactionId = 1;
    private final BankAccountService bankAccountService;

    public TransactionService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public void recordTransaction(long accountId, double amount, TransactionType type) {
        // Implement logic here
    }

    public List<Transaction> getTransactionsForAccount(long accountId) {
        // Implement logic here
        return null; // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }


}
