package com.test.carparking.service;


import com.test.carparking.entity.Account;
import com.test.carparking.entity.Parking;
import com.test.carparking.repos.AccountRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean isFreeAccount(String email) throws NotFoundException {
        Account entity = accountRepository.getAccountByEmail(email);

        if (entity == null)
            throw new NotFoundException("The Account with email " + email + " does not exist");

        return (!entity.getEmail().equals(email));
    }

    @Override
    public Account createAccount(Account acc) {
        //бронирование парковки
        Account entity = accountRepository.getAccountByEmail(acc.getEmail());
        if(entity != null)
            return entity;

        Account account = new Account();
        account.setEmail(acc.getEmail());
        account.setFirstName(acc.getFirstName());
        account.setLastName(acc.getLastName());
        account.setRegDate(LocalDateTime.now());
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) throws NotFoundException{
       Optional<Account> acc=  accountRepository.findById(id);
       if(acc.isPresent())
            return acc.get();
       else
           throw new NotFoundException("not found account by id: " + id);

       //return null;
        //return accountRepository.getAccountById(id);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
