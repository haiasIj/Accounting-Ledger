package com.pluralsight;

import java.time.LocalDate;

public class Transaction {
private LocalDate date;
private double amount;
private String description;
private String payment;
private String type;

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getPayment() {
        return payment;
    }
    public String getType() {
        return type;
    }

    public Transaction(LocalDate date, double amount, String description, String payment, String type) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.payment = payment;
        this.type = type;
    }
}
