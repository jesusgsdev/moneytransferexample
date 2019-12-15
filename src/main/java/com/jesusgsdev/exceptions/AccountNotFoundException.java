package com.jesusgsdev.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String BBAN) {
        super("Account " + BBAN + " not found");
    }
}
