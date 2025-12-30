package com.example.sample.sample1.dto.response;

import java.math.BigDecimal;

public class CreateAccountResponse {

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private Long accountId;
    private BigDecimal balance;

    public CreateAccountResponse(Long accountId, BigDecimal balance){
        this.accountId = accountId;
        this.balance = balance;
    }

}
