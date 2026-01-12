package com.example.sample.sample1.exception;

public class InsufficientFundsException extends RuntimeException {
    private final String accountId;
    private final String availableBalance;
    private final String transactionAmount;
    public InsufficientFundsException(String message, String accountId, String availableBalance, String transactionAmount) {
        super(message);
        this.accountId = accountId;
        this.availableBalance = availableBalance;
        this.transactionAmount = transactionAmount;
    }
    public String getAccountId() { return accountId;}
    public String getAvailableBalance() { return availableBalance;}
    public String getTransactionAmount() { return transactionAmount;}
}
