package com.jesusgsdev.httphandler;

import com.jesusgsdev.domain.Account;
import com.jesusgsdev.dto.TransferDTO;
import com.jesusgsdev.services.AccountService;
import com.jesusgsdev.services.TransferService;
import io.javalin.http.Context;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MoneyTransferHandlerTest {

    private MoneyTransferHandler moneyTransferHandler;
    private AccountService accountService;
    private TransferService transferService;

    MoneyTransferHandlerTest() {
        this.accountService = mock(AccountService.class);
        this.transferService = mock(TransferService.class);
        this.moneyTransferHandler = new MoneyTransferHandler(accountService, transferService);
    }

    @Test
    @DisplayName("Get account when the account exists")
    void handleGetAccountBalanceTest() {
        //Given
        Context context = mock(Context.class);
        when(context.pathParam(eq("iban"))).thenReturn("GB26MIDL40051512345691");
        final Account account = new Account("GB26MIDL40051512345691", 0.0, "GBP");
        when(this.accountService.getAccount(eq(account.getIBAN()))).thenReturn(account);

        //When
        moneyTransferHandler.handleGetAccountBalance(context);

        //Then
        verify(context).result("Your balance is " + account.getCurrency() + " " + account.getAvailable());
        verify(context).status(200);
        verify(accountService).getAccount(account.getIBAN());
    }

    @Test
    @DisplayName("Handle transfer when balance in sender account is enough")
    void handleTransferTest() {
        //Given
        Context context = mock(Context.class);
        final TransferDTO transferDTO = new TransferDTO("GB26MIDL40051512345691", "GB26MIDL40051512345692", 100.0);
        when(context.bodyAsClass(eq(TransferDTO.class))).thenReturn(transferDTO);

        final Account senderAccount = new Account("GB26MIDL40051512345691", 1000.0, "GBP");
        final Account recipientAccount = new Account("GB26MIDL40051512345692", 0.0, "GBP");
        when(this.accountService.getAccount(eq(senderAccount.getIBAN()))).thenReturn(senderAccount);
        when(this.accountService.getAccount(eq(recipientAccount.getIBAN()))).thenReturn(recipientAccount);

        final UUID transferId = UUID.randomUUID();
        when(this.transferService.executeTransfer(eq(transferDTO.getAmount()), eq(senderAccount), eq(recipientAccount)))
                .thenReturn(transferId);

        //When
        moneyTransferHandler.handleTransfer(context);

        //Then
        verify(context).result("Transfer was successful. Reference: " + transferId);
        verify(context).status(201);
        verify(accountService).getAccount(senderAccount.getIBAN());
        verify(accountService).getAccount(recipientAccount.getIBAN());
        verify(transferService).executeTransfer(transferDTO.getAmount(), senderAccount, recipientAccount);
    }

}
