package com.jesusgsdev.dto;

public class TransferDTO {

    private String senderAccountIBAN;
    private String recipientAccountIBAN;
    private Double amount;

    public TransferDTO() {
    }

    public TransferDTO(String senderAccountIBAN, String recipientAccountIBAN, Double amount) {
        this.senderAccountIBAN = senderAccountIBAN;
        this.recipientAccountIBAN = recipientAccountIBAN;
        this.amount = amount;
    }

    public String getSenderAccountIBAN() {
        return senderAccountIBAN;
    }

    public void setSenderAccountIBAN(String senderAccountIBAN) {
        this.senderAccountIBAN = senderAccountIBAN;
    }

    public String getRecipientAccountIBAN() {
        return recipientAccountIBAN;
    }

    public void setRecipientAccountIBAN(String recipientAccountIBAN) {
        this.recipientAccountIBAN = recipientAccountIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransferDTO that = (TransferDTO) o;

        if (!senderAccountIBAN.equals(that.senderAccountIBAN)) return false;
        if (!recipientAccountIBAN.equals(that.recipientAccountIBAN)) return false;
        return amount.equals(that.amount);
    }

    @Override
    public int hashCode() {
        int result = senderAccountIBAN.hashCode();
        result = 31 * result + recipientAccountIBAN.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TransferDTO{" +
                "senderAccountIBAN='" + senderAccountIBAN + '\'' +
                ", recipientAccountIBAN='" + recipientAccountIBAN + '\'' +
                ", amount=" + amount +
                '}';
    }
}
