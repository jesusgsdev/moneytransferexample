package com.jesusgsdev.httphandler;

import com.jesusgsdev.domain.Account;
import com.jesusgsdev.dto.TransferDTO;
import com.jesusgsdev.services.AccountService;
import com.jesusgsdev.services.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.http.Context;

import java.util.UUID;

public class MoneyTransferHandler {

    private static Logger logger = LoggerFactory.getLogger(MoneyTransferHandler.class);

    private AccountService accountService;
    private TransferService transferService;

    public MoneyTransferHandler(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    public void handleGetAccountBalance(Context ctx) {
        final String IBAN = ctx.pathParam("iban");
        logger.info("Received a balance request for the account {}", IBAN);
        final Account account = accountService.getAccount(IBAN);

        logger.info("Balance for the account {} retrieved successfully. Current balance is: {}{}",
                IBAN, account.getCurrency(), account.getAvailable());
        ctx.result("Your balance is " + account.getCurrency() + " " + account.getAvailable());
        ctx.status(200);
    }

    public void handleTransfer(Context ctx) {
        final TransferDTO transferDTO = ctx.bodyAsClass(TransferDTO.class);
        logger.info("Transfer of {} received to be executed from {} to {}", transferDTO.getAmount(),
                transferDTO.getSenderAccountIBAN(), transferDTO.getRecipientAccountIBAN());
        final Account senderAccount = accountService.getAccount(transferDTO.getSenderAccountIBAN());
        final Account recipientAccount = accountService.getAccount(transferDTO.getRecipientAccountIBAN());

        final UUID transferId = transferService.executeTransfer(transferDTO.getAmount(), senderAccount, recipientAccount);
        accountService.updateAccountsBalance(transferDTO.getAmount(), senderAccount, recipientAccount);
        logger.info("Transfer of {} from {} to {} was successful with Reference {}", transferDTO.getAmount(),
                transferDTO.getSenderAccountIBAN(), transferDTO.getRecipientAccountIBAN(), transferId);

        ctx.status(201);
        ctx.result("Transfer was successful. Reference: " + transferId);
    }

}