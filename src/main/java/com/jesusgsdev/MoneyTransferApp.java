package com.jesusgsdev;

import com.jesusgsdev.dao.AccountDao;
import com.jesusgsdev.dao.TransferDao;
import com.jesusgsdev.exceptions.AccountNotFoundException;
import com.jesusgsdev.exceptions.TransferException;
import com.jesusgsdev.httphandler.MoneyTransferHandler;
import com.jesusgsdev.services.AccountService;
import com.jesusgsdev.services.TransferService;
import io.javalin.Javalin;

public class MoneyTransferApp {

    private static AccountService accountService = new AccountService(new AccountDao());
    private static TransferService transferService = new TransferService(new TransferDao());
    private static MoneyTransferHandler moneyTransferHandler = new MoneyTransferHandler(accountService, transferService);

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
                    config.defaultContentType = "application/json";
                    config.asyncRequestTimeout = 10_000L;
                }
        ).start(7000);

        app.get("/account/balance/:iban", moneyTransferHandler::handleGetAccountBalance);
        app.post("/transfer", moneyTransferHandler::handleTransfer);

        app.exception(NullPointerException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result(e.getMessage());
        });

        app.exception(AccountNotFoundException.class, (e, ctx) -> {
            ctx.status(404);
            ctx.result(e.getMessage());
        });

        app.exception(TransferException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.result(e.getMessage());
        });

    }

}