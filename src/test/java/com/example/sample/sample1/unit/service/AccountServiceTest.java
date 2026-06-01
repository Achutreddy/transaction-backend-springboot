package com.example.sample.sample1.unit.service;


import com.example.sample.sample1.dto.request.CreateAccountRequest;
import com.example.sample.sample1.dto.response.CreateAccountResponse;
import com.example.sample.sample1.model.Account;
import com.example.sample.sample1.repository.AccountRepository;
import com.example.sample.sample1.service.AccountService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Account Service - Unit Tests")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    private Account testAccount;
    private CreateAccountRequest createAccountRequest;

    @BeforeEach
    void setUp(){
        testAccount = new Account();
        testAccount.setBalance(new BigDecimal("1000"));

        createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setBalance(new BigDecimal("5000"));
    }

    @Test
    @DisplayName("Should create an account successfully")
    void testCreateAccount(){
        //Arrange
        Account savedAccount = new Account();
        savedAccount.setBalance(createAccountRequest.getBalance());
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        //Act
        CreateAccountResponse response = accountService.createAccount(createAccountRequest);

        //Assert
        assertNotNull(response);
        assertEquals(createAccountRequest.getBalance(),response.getBalance());
    }

    @Test
    @DisplayName("Shall retrieve all accounts")
    void testFindAll(){
        //Arrange
        List<Account> accountList = Arrays.asList(testAccount,new Account());
        when(accountRepository.findAll()).thenReturn(accountList);

        //Act
        List<Account> result = accountService.findAll();

        //Assert
        assertNotNull(result);
        assertEquals(2,result.size());
    }

    @Test
    @DisplayName("Should handle empty account list")
    void testFindAllEmpty(){
        //Arrange
        when(accountRepository.findAll()).thenReturn(List.of());

        //Act
        List<Account> result = accountService.findAll();

        //Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
