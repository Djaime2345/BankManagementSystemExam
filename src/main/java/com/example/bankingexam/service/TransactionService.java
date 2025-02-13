package com.example.bankingexam.service;

import com.example.bankingexam.model.Transaction;
import com.example.bankingexam.model.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Comparator;

@Service
public class TransactionService {
    private final ConcurrentHashMap<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();
    private long currentTransactionId = 1;
    private final BankAccountService bankAccountService;

    public TransactionService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public void recordTransaction(long accountId, double amount, TransactionType type) {
        // Check if account exits
        try{
            bankAccountService.getAccount(accountId);
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }

        // Check if amount is correct for transaction
        if (amount <= 0){
            throw new RuntimeException("Amount must be greater than 0");
        }

        // Create new list of transactions if it is new in the history, if not, just add it
        Transaction transaction = new Transaction(this.currentTransactionId++, accountId, amount, type, LocalDateTime.now());

        if (transactions.containsKey(accountId)){
            transactions.get(accountId).add(transaction);
        } else {
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);
            transactions.put(accountId, transactionList);
        }
    }

    public List<Transaction> getTransactionsForAccount(long accountId) {
        // Implement logic here
        List<Transaction> accountTransactions = new ArrayList<>();

        if (transactions.containsKey(accountId)){
            return transactions.get(accountId);
        } else {
            return accountTransactions;
        }
        // Temporary return, you should replace it with the appropriate value according to the method's logic.
    }


}
