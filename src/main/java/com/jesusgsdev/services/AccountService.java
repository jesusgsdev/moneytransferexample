package com.jesusgsdev.services;

import com.jesusgsdev.exceptions.AccountNotFoundException;
import com.jesusgsdev.dao.AccountDao;
import com.jesusgsdev.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccount(final String IBAN) {
        return accountDao.get(IBAN).orElseThrow(() -> new AccountNotFoundException(IBAN));
    }

    public void updateAccountsBalance(final Double amount, final Account senderAccount, final Account recipientAccount) {
        senderAccount.setAvailable(senderAccount.getAvailable() - amount);
        accountDao.save(senderAccount);
        logger.info("Balance for account {} was be decreased in {}, {} is available", senderAccount.getIBAN(),
                amount, senderAccount.getAvailable());

        recipientAccount.setAvailable(recipientAccount.getAvailable() + amount);
        accountDao.save(recipientAccount);
        logger.info("Balance for account {} was increased in {}, {} is available", recipientAccount.getIBAN(),
                amount, recipientAccount.getAvailable());
    }

}
