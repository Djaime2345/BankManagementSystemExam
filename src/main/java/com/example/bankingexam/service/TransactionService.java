package com.example.bankingexam.service;

import com.example.bankingexam.model.Transaction;
import com.example.bankingexam.model.TransactionType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service Class for transactions
 */
@Service
public class TransactionService {
    private final ConcurrentHashMap<Long, List<Transaction>> transactions = new ConcurrentHashMap<>();
    private long currentTransactionId = 1;
    private final BankAccountService bankAccountService;

    /**
     * Constructor for transaction service
     *
     * @param bankAccountService
     */
    public TransactionService(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Method that record a transaction into the system. It saves the transactions for the same account in a list
     *
     * @param accountId ID of the bank account
     * @param amount Money for the transaction
     * @param type Type of transaction (Deposit or Withdraw)
     */
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

    /**
     * Method that return the list of transactions of a bank account
     *
     * @param accountId ID of the bank account
     * @return List of transactions of that bank account
     */
    public List<Transaction> getTransactionsForAccount(long accountId) {
        List<Transaction> accountTransactions = new ArrayList<>();

        if (transactions.containsKey(accountId)){
            return transactions.get(accountId);
        } else {
            return accountTransactions;
        }
    }

}
