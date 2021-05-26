package org.vilutis.lt.pts.services.api;

import java.util.Optional;
import org.vilutis.lt.pts.model.Account;

public interface AccountService {

    Optional<Account> getAccountByName(String accountName);
}
