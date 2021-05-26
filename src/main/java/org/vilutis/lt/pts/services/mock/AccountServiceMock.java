package org.vilutis.lt.pts.services.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.vilutis.lt.pts.model.Account;
import org.vilutis.lt.pts.services.api.AccountService;

@Service
@Profile("mock")
public class AccountServiceMock implements AccountService {

    private final List<Account> MOCK_ACCOUNTS;

    @Autowired
    public AccountServiceMock(ObjectMapper mapper) throws IOException {
        File src = new File("src/main/resources/mockData/accounts.json");
        Account[] accounts = mapper.readValue(src, Account[].class);
        MOCK_ACCOUNTS = Collections.unmodifiableList(Arrays.asList(accounts));
    }

    @Override
    public Optional<Account> getAccountByName(String accountName) {
        return MOCK_ACCOUNTS.stream()
          .filter(account -> account.getAccountName().equals(accountName))
          .findFirst();
    }
}
