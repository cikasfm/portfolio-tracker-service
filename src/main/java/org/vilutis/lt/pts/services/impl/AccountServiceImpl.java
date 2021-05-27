package org.vilutis.lt.pts.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.repository.AccountRepository;
import org.vilutis.lt.pts.services.api.AccountService;

@Service
@Profile("!mock")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> getAccountByName(String accountName) {
        return repository.findOneByAccountName(accountName);
    }
}
