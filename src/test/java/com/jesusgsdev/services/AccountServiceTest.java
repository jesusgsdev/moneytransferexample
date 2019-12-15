package com.jesusgsdev.services;

import com.jesusgsdev.dao.AccountDao;
import com.jesusgsdev.domain.Account;
import com.jesusgsdev.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private AccountDao accountDao;
    private AccountService accountService;

    AccountServiceTest() {
        this.accountDao = mock(AccountDao.class);
        this.accountService = new AccountService(accountDao);
    }

    @Test
    @DisplayName("Get account when the account exists")
    void getWhenAccountExistsTest() {
        //Given
        Account account = new Account("GB26MIDL40051512345691", 0.0, "GBP");
        when(this.accountDao.get(eq(account.getIBAN()))).thenReturn(Optional.of(account));

        //When
        Account returnedAccount = accountService.getAccount(account.getIBAN());

        //Then
        assertNotNull(returnedAccount);
        assertEquals(account, returnedAccount);
        verify(accountDao).get(eq(account.getIBAN()));
    }

    @Test
    @DisplayName("Get account when the account does not exist will throw an exception")
    void getWhenAccountDoesNotExistsTest() {
        //Given
        final String IBAN = "GB26MIDL40051512345691";
        final String expectedMessage = "Account " + IBAN + " not found";
        when(this.accountDao.get(eq(IBAN))).thenThrow(new AccountNotFoundException(IBAN));

        //When
        AccountNotFoundException exception =
                Assertions.assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(IBAN));

        //Then
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        verify(accountDao).get(eq(IBAN));
    }

    @Test
    @DisplayName("Update Account Balance to decrease in one and increase in the other")
    void updateAccountBalanceWhenATransferIsSentTest() {
        //Given
        final Double amount = 100.0;
        final Account senderAccount = new Account("GB26MIDL40051512345691", 1000.0, "GBP");
        final Account recipientAccount = new Account("GB26MIDL40051512345692", 0.0, "GBP");
        when(this.accountDao.save(eq(senderAccount))).thenReturn(senderAccount);
        when(this.accountDao.save(eq(recipientAccount))).thenReturn(recipientAccount);

        //When
        accountService.updateAccountsBalance(amount, senderAccount, recipientAccount);

        //Then
        assertEquals(900.0, senderAccount.getAvailable());
        assertEquals(100.0, recipientAccount.getAvailable());
        verify(accountDao).save(eq(senderAccount));
        verify(accountDao).save(eq(recipientAccount));
    }

}
