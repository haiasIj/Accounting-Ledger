package com.pluralsight;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Comparator;

//LETS SORT TRANSACTIONS THE WAY I WANT TO, REVERSE CHRONOLOGICAL ORDER
public class ListParse {
    //ARRAY LIST TIME
    public static ArrayList<Transaction> loadTransactions() {
        //USE ARRAY LIST TO READ FROM TRANSACTIONS.CSV
        try {
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Transaction> listParse = new ArrayList<>();
            String input;
            //ignores first line from transactions.csv
            while ((input = bufferedReader.readLine()) != null) {
                if (input.startsWith("date")) {
                    continue;
                }

                //READS FROM TRANSACTIONS.CSV TO SORT IN DIFFERENT WAYS
                String[] items = input.split(" \\| ");
                LocalDateTime dateTime = LocalDateTime.parse(items[0] + " | " + items[1], DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss"));
                String description = items[2];
                String vendor = items[3];
                float price = Float.parseFloat(items[4]);
                listParse.add(new Transaction(dateTime, description, vendor, price));
            }
            //SORTS IN REVERSE CHRONOLOGICAL ORDER AND RETURNS PARSED LIST
            listParse.sort(Comparator.comparing(Transaction::getNow).reversed());

            return listParse;
            //CATCHES EXCEPTIONS YAY NO BREAK CODE AS MUCH
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


