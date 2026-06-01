package com.example.sample.sample1.unit.service;

import com.example.sample.sample1.exception.InsufficientFundsException;
import com.example.sample.sample1.model.Account;
import com.example.sample.sample1.model.Transaction;
import com.example.sample.sample1.model.TransactionStatus;
import com.example.sample.sample1.repository.AccountRepository;
import com.example.sample.sample1.repository.TransactionRepository;
import com.example.sample.sample1.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("Transaction service - Unit Tests")
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private TransactionService transactionService;

    private Transaction testTransaction;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setBalance(new BigDecimal("10000"));

        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setAccountId(1L);
        testTransaction.setAmount(100.0);
        testTransaction.setTransactionType("CREDIT");
        testTransaction.setDescription("Test Transaction");
    }

    @Test
    void testFindAll() {
        //Arrange
        List<Transaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findAll()).thenReturn(transactions);

        //Act
        List<Transaction> result = transactionService.findAll();

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should perform CREDIT Transaction Successfully")
    void testSaveCreditTransaction() {
        //Arrange
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(testAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        //Act
        Transaction result = transactionService.save(testTransaction);

        //Assert
        assertNotNull(result);
        assertEquals(TransactionStatus.CREATED, result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Should perform DEBIT Transaction Successfully")
    void testSaveDebitTransaction() {
        //Arrange
        testTransaction.setTransactionType("DEBIT");
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(testAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        //Act
        Transaction result = transactionService.save(testTransaction);

        //Assert
        assertNotNull(result);
        assertEquals(TransactionStatus.CREATED, result.getStatus());
    }

    @Test
    @DisplayName("Shall throw exception when Account not found")
    void testSaveTransactionAccountNotFound() {
        //Arrange
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transactionService.save(testTransaction));
    }

    @Test
    @DisplayName("Shall throw Insufficient Funds exception for DEBIT Transaction")
    void testSaveDebitTransactionInsufficientFunds() {
        //Arrange
        testTransaction.setTransactionType("DEBIT");
        testAccount.setBalance(new BigDecimal("100"));
        testTransaction.setAmount(1000.0);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(testAccount));

        //Act & Assert
        assertThrows(InsufficientFundsException.class, () -> transactionService.save(testTransaction));
    }

}
