package com.example.sample.sample1.service;

import com.example.sample.sample1.exception.InsufficientFundsException;
import com.example.sample.sample1.model.Account;
import com.example.sample.sample1.model.Transaction;
import com.example.sample.sample1.model.TransactionStatus;
import com.example.sample.sample1.repository.AccountRepository;
import com.example.sample.sample1.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction save(Transaction transaction) {

        String type = transaction.getTransactionType().toUpperCase();
        BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
        Long accountId = transaction.getAccountId();
        Account account = accountRepository.findById(accountId).orElseThrow(()->new IllegalArgumentException("Account id not found - "+ accountId));

        if(type.equals("DEBIT")) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Your account has insufficient funds to make this transaction",
                        accountId.toString(), account.getBalance().toPlainString(),amount.toPlainString());
            }
            account.setBalance(account.getBalance().subtract(amount));
        }
        else if(type.equals("CREDIT")){
            account.setBalance(account.getBalance().add(amount));
        }
        transaction.setStatus(TransactionStatus.CREATED);
        transaction.setCreatedAt(LocalDateTime.now());
        return repository.save(transaction);
    }
    // Other methods like findById, update, delete...
}