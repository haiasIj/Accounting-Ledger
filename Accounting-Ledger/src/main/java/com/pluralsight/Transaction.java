package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction {
    private LocalDateTime now;
    private String description;
    private String vendor;
    private float price;

    public Transaction(String description, String vendor, float price) {
        this.now = LocalDateTime.now();
        this.description = description;
        this.vendor = vendor;
        this.price = price;
    }

    public Transaction(LocalDateTime dateTime, String description, String vendor, float price) {
        this.now = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
    }


    public Transaction(LocalDateTime now, String description, String vendor, double amount) {
        this.now = now;
        this.description = description;
        this.vendor = vendor;
        this.price = (float) amount;
    }

    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    try {
                        LocalDateTime dateTime = LocalDateTime.parse(parts[0].trim()); // ISO 8601: yyyy-MM-ddTHH:mm:ss
                        String description = parts[1].trim();
                        String vendor = parts[2].trim();
                        float price = Float.parseFloat(parts[3].trim());
                        transactions.add(new Transaction(dateTime, description, vendor, price));
                    } catch (Exception e) {
                        System.err.println("Failed to parse line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read transactions file: " + e.getMessage());
        }

        return transactions;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public float getPrice() {
        return price;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss | ");
        return String.format("%s|%-8s %-5s|%.2f\n", now.format(dateTimeFormatter), description, vendor, price);
    }
}

