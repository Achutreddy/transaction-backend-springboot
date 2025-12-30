package com.example.sample.sample1.service;

import com.example.sample.sample1.dto.request.CreateAccountRequest;
import com.example.sample.sample1.dto.response.CreateAccountResponse;
import com.example.sample.sample1.model.Account;
import com.example.sample.sample1.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public CreateAccountResponse createAccount( CreateAccountRequest request){
        Account account = new Account();
        account.setBalance(request.getBalance());

        Account savedAccount = accountRepository.save(account);

        return new CreateAccountResponse(savedAccount.getId(),
                                            savedAccount.getBalance());
    }
}
