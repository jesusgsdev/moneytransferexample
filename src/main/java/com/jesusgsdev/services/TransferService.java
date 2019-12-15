package com.jesusgsdev.services;

import com.jesusgsdev.dao.TransferDao;
import com.jesusgsdev.domain.Account;
import com.jesusgsdev.domain.Transfer;
import com.jesusgsdev.exceptions.TransferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferService {

    private static Logger logger = LoggerFactory.getLogger(TransferService.class);

    private TransferDao transferDao;

    public TransferService(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public UUID executeTransfer(final Double amount, final Account senderAccount, final Account recipientAccount) {
        validateBalanceIsEnough(amount, senderAccount);

        final Transfer transfer = new Transfer(UUID.randomUUID(), senderAccount.getIBAN(), recipientAccount.getIBAN(),
                amount, LocalDateTime.now());
        final Transfer savedTransfer = transferDao.save(transfer);
        logger.info("Transfer of {}{} completed from {} to {} accounts with Reference {}", senderAccount.getCurrency(),
                amount, senderAccount.getIBAN(), recipientAccount.getIBAN(), savedTransfer.getId());
        return savedTransfer.getId();
    }

    private void validateBalanceIsEnough(Double amount, Account senderAccount) {
        if (senderAccount.getAvailable() < amount) {
            logger.warn("There is not enough balance to transfer the {} amount from the sender account {}",
                    amount, senderAccount.getIBAN());
            throw new TransferException("There is no enough balance available on the sender account");
        }
    }

}
