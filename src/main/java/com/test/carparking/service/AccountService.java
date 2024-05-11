package com.test.carparking.service;

import com.test.carparking.entity.Account;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    boolean isFreeAccount(String email) throws NotFoundException;

    Account getAccountById(int id) throws NotFoundException;

    Account getAccountByEmail(String email);

    Account createAccount(Account acc);

    List<Account> getAccounts();

    Page<Account> getAllAccounts(Pageable pageable);
}
