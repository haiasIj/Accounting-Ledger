package com.pluralsight;

public class Payment {
    private String date;
    private double amount;
    private String vendor;
    private String description;

    public Payment(String date, double amount, String vendor, String description) {
        this.date = date;
        this.amount = amount;
        this.vendor = vendor;
        this.description = description;
    }
    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getVendor() {
        return vendor;
    }


    public String getDescription() {
        return description;
    }

}

