package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class App {

    // Make scanner accessible to all methods
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader("src/main/resources/transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input;

            while ((input = bufReader.readLine()) != null) {
                System.out.println(input);
            }

            bufReader.close();
        } catch (IOException e) { //Prevents program from crashing
            e.printStackTrace();
        }

        System.out.println("Welcome to the Financial Transactions App. What would you like to do?");

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("\tD- Add Deposit\n" +
                    "\tP- Make Payment\n" +
                    "\tL- Ledger\n" +
                    "\tX- Exit App\n");
            System.out.print("Enter Selection: ");
            String selection = scanner.nextLine();

            switch (selection.toUpperCase()) { //toUpperCase makes selections letters instead of numbers
                case "D":
                    addDeposit();
                    break;
                case "P":
                    displayPaymentMenu();
                    break;
                case "L":
                    displayLedgerMenu();
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Choose a valid selection");
            }
        }
    }

    static List<Deposit> deposits = new ArrayList<>();

    public static void addDeposit() {
        System.out.print("Enter deposit date (YYYY-MM-DD): ");
        String date = App.scanner.nextLine();

        System.out.print("Enter deposit amount: ");
        double amount = App.scanner.nextDouble();
        App.scanner.nextLine(); // consume newline

        System.out.print("Enter deposit description: ");
        String description = App.scanner.nextLine();

        Deposit deposit = new Deposit(date, amount, description);
        App.deposits.add(deposit);

        System.out.println("Deposit successfully added.");
    }

    static List<Payment> payments = new ArrayList<>();

    public static void displayPaymentMenu() {
        System.out.print("Enter payment date (YYYY-MM-DD): ");
        String date = App.scanner.nextLine();

        System.out.print("Enter payment amount: ");
        double amount = App.scanner.nextDouble();
        App.scanner.nextLine(); // consume newline

        System.out.print("Enter vendor name: ");
        String vendor = App.scanner.nextLine();

        System.out.print("Enter deposit description: ");
        String description = App.scanner.nextLine();

        Payment payment = new Payment(date, amount, vendor, description);
        payments.add(payment);

        System.out.println("Payment successfully recorded");
    }

    public static void displayLedgerMenu() {
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("\tA- Display All Entries\n" +
                    "\tD- Display Deposit Entries\n" +
                    "\tP- Display Payments (Negative Entries)\n" +
                    "\tR- Reports\n" +
                    "\tH- Home\n");
            System.out.print("Enter Selection: ");
            String selection = scanner.nextLine();

            switch (selection.toUpperCase()) {
                case "A":
                    displayAllEntries();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    displayReports();
                    break;
                case "H":

                default:
                    System.out.println("Choose a valid selection");
            }
        }
    }



    public static void displayAllEntries(List<Transaction> entries) {
        if (entries.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        entries.sort(Comparator.comparing(Transaction::getDate).reversed()); //sorts entries from oldest to newest until using reversed

        System.out.println("\n All Transactions (Newest First) ");
        System.out.printf("%-12s | %-8s | %-15s\n", "Date", "Amount", "Description");

        for (Transaction t : entries) { //rename "Transaction" to "t" for short
            String sign = t.getType().equals("DEPOSIT") ? "+" : "-";
            System.out.printf("%-12s | %s%-8.2f | %-15s\n", t.getDate(), sign, t.getAmount(), t.getDescription());
        }

        System.out.println();
    }

    public static void displayDeposits(List<Transaction> entries) {
        if (entries.isEmpty()) {
            System.out.println("No transactions found.\n");
            return;
        }
        for (Transaction t : entries) {
            if (t.getType().equalsIgnoreCase("deposit")) {  // Check if it's a deposit
                System.out.printf("%2s | %s | %2f | %s\n",
                        t.getDate(), "+",  // Deposit symbol
                        t.getAmount(),
                        t.getDescription());
            }
        }
    }
    public static void displayPayments() {

    }
    public static void displayReports() {

    }
}





