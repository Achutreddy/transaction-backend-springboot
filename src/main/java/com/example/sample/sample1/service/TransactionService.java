package com.example.sample.sample1.service;

import com.example.sample.sample1.model.Transaction;
import com.example.sample.sample1.model.TransactionStatus;
import com.example.sample.sample1.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction save(Transaction transaction) {
        transaction.setStatus(TransactionStatus.CREATED);
        transaction.setCreatedAt(LocalDateTime.now());
        return repository.save(transaction);
    }
    // Other methods like findById, update, delete...
}