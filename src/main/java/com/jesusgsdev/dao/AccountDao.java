package com.jesusgsdev.dao;

import com.jesusgsdev.domain.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountDao {

    private Map<String, Account> accounts = new HashMap(
            Map.of(
            "GB26MIDL40051512345671", new Account("GB26MIDL40051512345671",  1000.0D, "GBP"),
            "GB26MIDL40051512345675", new Account("GB26MIDL40051512345675",  100.0D, "GBP"),
            "GB26MIDL40051512345679", new Account("GB26MIDL40051512345679",  0.0D, "GBP")
    ));

    public Optional<Account> get(final String IBAN) {
        return Optional.ofNullable(accounts.get(IBAN));
    }

    public Account save(final Account senderAccount) {
        accounts.put(senderAccount.getIBAN(), senderAccount);
        return senderAccount;
    }
}
