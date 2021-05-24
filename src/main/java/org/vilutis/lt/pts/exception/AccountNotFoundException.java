package org.vilutis.lt.pts.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super(String.format("Account with number {} not found", accountNumber));
    }

}
