package com.jesusgsdev.domain;

public class Account {

    private String IBAN;
    private Double available;
    private String currency;

    public Account(String IBAN, Double available, String currency) {
        this.IBAN = IBAN;
        this.available = available;
        this.currency = currency;
    }

    public String getIBAN() {
        return IBAN;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return IBAN.equals(account.IBAN);
    }

    @Override
    public int hashCode() {
        return IBAN.hashCode();
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", available=" + available +
                '}';
    }
}
