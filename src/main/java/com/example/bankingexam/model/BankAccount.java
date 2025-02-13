package com.example.bankingexam.model;

public class BankAccount {
    private long id;
    private String accountHolder;
    private double balance;

    // Constructor actualizado
    public BankAccount(long id, String accountHolder, double balance) {
        this.id = id;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
