package com.test.carparking.repos;


import com.test.carparking.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findAll(Pageable pageable);

    Account getAccountByEmail(String email);

    Account getAccountById(int acccuntId);
}
