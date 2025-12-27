package com.example.sample.sample1.repository;


import com.example.sample.sample1.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Extends JpaRepository, providing CRUD methods (save, findById, findAll, etc.)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Custom query methods can be added here, e.g.,
    // List<Transaction> findByTransactionType(String type);
}