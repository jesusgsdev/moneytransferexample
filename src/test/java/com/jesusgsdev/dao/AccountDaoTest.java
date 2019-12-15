package com.jesusgsdev.dao;

import com.jesusgsdev.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    private AccountDao accountDao;

    AccountDaoTest() {
        this.accountDao = new AccountDao();
    }

    @Test
    @DisplayName("Get when the account exists")
    void getWhenAccountExistsTest(){
        //Given
        String  IBAN = "GB26MIDL40051512345671";

        //When
        Optional<Account> account = accountDao.get(IBAN);

        //Then
        assertTrue(account.isPresent());
        assertEquals(IBAN, account.get().getIBAN());
    }

    @Test
    @DisplayName("Get when the account does not exists")
    void getWhenAccountDoesNotExistsTest(){
        //Given
        String  IBAN = "GB26MIDL40051512345670";

        //When
        Optional<Account> account = accountDao.get(IBAN);

        //Then
        assertFalse(account.isPresent());
    }

    @Test
    @DisplayName("Save a new account")
    void saveWhenIsANewAccountTest(){
        //Given
        Account newAccount = new Account("GB26MIDL40051512345691", 0.0, "GBP");

        //When
        Account account = accountDao.save(newAccount);

        //Then
        assertNotNull(account);
        assertEquals(newAccount, account);
    }

    @Test
    @DisplayName("Save to update an Existing account to update the balance")
    void saveWhenIsExistingAccountTest(){
        //Given
        String  IBAN = "GB26MIDL40051512345671";
        Account existingAccount = accountDao.get(IBAN).get();
        existingAccount.setAvailable(500.0);

        //When
        Account account = accountDao.save(existingAccount);

        //Then
        assertNotNull(account);
        assertEquals(500.0, account.getAvailable());
    }

}
