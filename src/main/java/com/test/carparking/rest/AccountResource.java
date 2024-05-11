package com.test.carparking.rest;

import com.test.carparking.entity.Account;
import com.test.carparking.exception.NotBlockedException;
import com.test.carparking.exception.NotFreeException;
import com.test.carparking.service.AccountService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/all")
    public Page<Account> getAllAccounts(@RequestParam(defaultValue = "0", required = false) int page,
                                        @RequestParam(defaultValue = "20", required = false) int size) {
        //получить список всех парковочных мест (через pagination)
        Pageable paging = PageRequest.of(page, size, Sort.by("id"));
        Page<Account> list = accountService.getAllAccounts(paging);
        return list;
    }

    @GetMapping("/accounts/is_free/{email}")
    public ResponseEntity<Boolean> checkIsFreeAccount(@PathVariable String email) throws NotFoundException {
        Boolean res = accountService.isFreeAccount(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/accounts/create/")
    public ResponseEntity<Account> blockParking(@PathVariable Account account) throws NotFreeException {
        //въезд авто - блокировка парковочного места
        Account acc = accountService.createAccount(account);
        if (acc == null)
            throw new NotFreeException("The account was not created");

        return new ResponseEntity<>(acc, HttpStatus.OK);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException ex) {
        //когда не найдено парковочное место
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFreeException.class)
    public ResponseEntity<String> handleException(NotFreeException ex) {
        //когда не свободно парковочное место
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotBlockedException.class)
    public ResponseEntity<String> handleException(NotBlockedException ex) {
        //когда не забронировано парковочное место
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
