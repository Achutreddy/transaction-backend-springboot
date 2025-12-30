package com.example.sample.sample1.repository;

import com.example.sample.sample1.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
