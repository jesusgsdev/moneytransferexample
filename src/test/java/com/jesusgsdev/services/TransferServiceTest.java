package com.jesusgsdev.services;

import com.jesusgsdev.dao.AccountDao;
import com.jesusgsdev.dao.TransferDao;
import com.jesusgsdev.domain.Account;
import com.jesusgsdev.domain.Transfer;
import com.jesusgsdev.exceptions.TransferException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private TransferDao transferDao;
    private TransferService transferService;

    TransferServiceTest() {
        this.transferDao = mock(TransferDao.class);
        this.transferService = new TransferService(transferDao);
    }

    @Test
    @DisplayName("Execute a transfer when the balance in the sender account is enough")
    void executeTransferWhenBalanceIsEnoughTest() {
        //Given
        final Double amount = 100.0;
        final Account senderAccount = new Account("GB26MIDL40051512345691", 1000.0, "GBP");
        final Account recipientAccount = new Account("GB26MIDL40051512345692", 0.0, "GBP");
        final Transfer transfer = new Transfer(UUID.randomUUID(), senderAccount.getIBAN(), recipientAccount.getIBAN(),
                amount, LocalDateTime.now());
        when(this.transferDao.save(any(Transfer.class))).thenReturn(transfer);

        //When
        UUID transferId = transferService.executeTransfer(amount, senderAccount, recipientAccount);

        //Then
        assertNotNull(transferId);
        assertEquals(transfer.getId(), transferId);
        verify(transferDao).save(any(Transfer.class));
    }

    @Test
    @DisplayName("Execute a transfer when the balance in the sender account is not enough")
    void executeTransferWhenBalanceIsNotEnoughTest() {
        //Given
        final Double amount = 10000.0;
        final Account senderAccount = new Account("GB26MIDL40051512345691", 1000.0, "GBP");
        final Account recipientAccount = new Account("GB26MIDL40051512345692", 0.0, "GBP");
        final Transfer transfer = new Transfer(UUID.randomUUID(), senderAccount.getIBAN(), recipientAccount.getIBAN(),
                amount, LocalDateTime.now());
        when(this.transferDao.save(any(Transfer.class))).thenReturn(transfer);

        //When
        TransferException exception = Assertions.assertThrows(TransferException.class,
                () -> transferService.executeTransfer(amount, senderAccount, recipientAccount));

        //Then
        assertNotNull(exception);
        assertEquals("There is no enough balance available on the sender account", exception.getMessage());
        verifyNoInteractions(transferDao);
    }
}
