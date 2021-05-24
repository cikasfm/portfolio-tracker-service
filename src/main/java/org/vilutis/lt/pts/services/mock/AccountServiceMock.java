package org.vilutis.lt.pts.services.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.services.AccountService;

@Service
@Profile("mock")
public class AccountServiceMock implements AccountService {

    public Account findAccount(String accountNumber) {
        return new Account(accountNumber);
    }

}
