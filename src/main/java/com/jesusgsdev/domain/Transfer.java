package com.jesusgsdev.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transfer {

    private UUID id;
    private String senderAccountIBAN;
    private String recipientAccountIBAN;
    private Double amount;
    private LocalDateTime ceaetionDateTime;

    public Transfer(UUID id, String senderAccountIBAN, String recipientAccountIBAN, Double amount, LocalDateTime ceaetionDateTime) {
        this.id = id;
        this.senderAccountIBAN = senderAccountIBAN;
        this.recipientAccountIBAN = recipientAccountIBAN;
        this.amount = amount;
        this.ceaetionDateTime = ceaetionDateTime;
    }

    public UUID getId() {
        return id;
    }

    public String getSenderAccountIBAN() {
        return senderAccountIBAN;
    }

    public String getRecipientAccountIBAN() {
        return recipientAccountIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfer = (Transfer) o;

        if (!id.equals(transfer.id)) return false;
        if (!senderAccountIBAN.equals(transfer.senderAccountIBAN)) return false;
        if (!recipientAccountIBAN.equals(transfer.recipientAccountIBAN)) return false;
        if (!amount.equals(transfer.amount)) return false;
        return ceaetionDateTime.equals(transfer.ceaetionDateTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + senderAccountIBAN.hashCode();
        result = 31 * result + recipientAccountIBAN.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + ceaetionDateTime.hashCode();
        return result;
    }
}