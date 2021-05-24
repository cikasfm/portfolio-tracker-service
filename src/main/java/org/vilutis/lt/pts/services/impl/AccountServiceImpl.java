package org.vilutis.lt.pts.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.exception.AccountNotFoundException;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.repository.AccountRepository;
import org.vilutis.lt.pts.services.AccountService;

@Service
@Profile("!mock")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    public Account findAccount(String accountNumber) {
        return repository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

}
