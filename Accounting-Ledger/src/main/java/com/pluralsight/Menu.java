package com.pluralsight;



import com.pluralsight.ListParse;
import com.pluralsight.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        chooseScreen();
    }

    //MAIN MENU START CHOOSE NEXT SCREEN TO GO TO
    public static void chooseScreen() {
        System.out.println("Welcome to your Personal Banking App! \n");

        //WHILE LOOP FOR HOMESCREEN MENU SELECTION
        while (true) {

            System.out.println("Please choose from the following options \n");

            System.out.println("What would you like to do?");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make a Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit \n");

            System.out.print("Please enter your letter selection here:  ");

            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "D": //GO TO FIRST PAGE (ADD DEPOSIT)
                    System.out.println("Continue to Deposit info (Please press Enter):  ");
                    scanner.nextLine();
                    depositInfo();
                    break;
                case "P": //GO TO THE SECOND PAGE (MAKE A PAYMENT)
                    System.out.println("Continue to Payment (Please press Enter):  ");
                    scanner.nextLine();
                    paymentInfo();
                    break;
                case "L": //CREATE LEDGER MENU TO CALL TO
                    System.out.println("Continue to Ledger Menu (Please press Enter): ");
                    scanner.nextLine();
                    displayLedger();
                    break;
                case "X": //EXIT OUT OF APP
                    System.exit(0);
                    break;
                default: //WHOOPSIE WAS DONE, REDO
                    System.out.println("Invalid option selected, please try again \n");
            }
        }
    }

    //DEPOSIT SCREEN: ENTER INFO TO RECORD DEPOSIT
    private static void depositInfo() {
        //ENTER USER INPUT TO DEFINE INSTANCE OF TRANSACTION
        System.out.println("Please enter some info to record a Deposit: \n");
        System.out.print("Please enter the type of Deposit: ");
        String description = scanner.nextLine();
        System.out.print("Please enter the Vendor Name: ");
        String vendor = scanner.nextLine();
        System.out.print("Please enter the Deposit Amount (only positive): ");
        float price = scanner.nextFloat();
        scanner.nextLine(); //CONSUME LINE CLRF
        //OPENS UP FILEWRITER IN APPEND MODE, OPENS FILE AND AUTOMATICALLY WRITES USERINPUT DIRECTLY INTO TRANSACATIONS.CSV
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            //CREATES A NEW INSTANCE OF A TRANSACTION. AS IS ONLY POSITIVE TRANSACTION WILL BE FLAGGED AS DEPOSIT ALWAYS
            //SEE TRANSACTION.JAVA FOR TOSTRING EXPLANATION
            bufferedWriter.write(new Transaction(description, vendor, price).toString());
            // CLOSES OUT WRITER AUTOMATICALLY HERE
        } catch (IOException ignored) {
        }
    }

    //PAYMENT SCREEN : ENTER INFO TO RECORD A DEBIT TRANSACTION
    private static void paymentInfo() {

        System.out.println("Please fill out some info to record a transaction: \n");
        System.out.print("Please enter the Item Purchased: ");
        String description = scanner.nextLine();
        System.out.print("Please enter the Vendor Name: ");
        String vendor = scanner.nextLine();
        System.out.print("Please enter the Transaction Amount (only negative): ");
        float price = scanner.nextFloat();
        scanner.nextLine();
        //APPEND MODE AGAIN JUST LIKE ABOVE
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {
            //WRITES PAYMENT INFO TO TRANSACTIONS.CSV by putting toString at end it adds date time info SEE IN TRANSACTIONS.JAVA
            bufferedWriter.write(new Transaction(description, vendor, price).toString());
        } catch (IOException ignored) {
        }
    }

    //LEDGER SCREEN
    private static void displayLedger() {
        System.out.println("Welcome to the Leger Menu\n");
        System.out.println("Please choose from the following options: \n");
        System.out.println("A) All - display all transactions");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        System.out.print("Please enter your letter selection here: ");

        String choice = scanner.nextLine().toUpperCase();
        switch (choice) {
            case "A": //DISPLAY ALL TRANSACTIONS
                System.out.println("Continue to full Ledger Display (Please press Enter):  ");
                scanner.nextLine();
                displayAllEntries();
                break;
            case "D": //DISPLAY ONLY DEPOSITS
                System.out.println("Continue Deposit Display (Please press Enter):  ");
                scanner.nextLine();
                displayDeposits();
                break;
            case "P": //DISPLAY ONLY PAYMENTS
                System.out.println("Continue to Payment Display (Please press Enter): ");
                scanner.nextLine();
                displayPayments();
                break;
            case "R": //GOES TO DISPLAY REPORTS SELECTOR
                System.out.println("Continue to Reports Screen (Please press Enter): ");
                scanner.nextLine();
                displayReports();
                break;
            case "H": //RETURNS TO HOME SCREEN
                System.out.println("Continue to the Home Screen (Please press Enter): ");
                scanner.nextLine();
                chooseScreen();
                break;
            default: //WHOOPSIE WAS DONE, REDO
                System.out.println("Invalid option selected, please try again \n");
        }
    }

    //ALL LEDGER ENTRIES SCREEN - SORTS BY TIME NEW TO OLD
    public static void displayAllEntries() {
        System.out.println("-Showing All Entries-");
        System.out.println("Newest Entries Shown first");
        System.out.println("date | time | description | vendor | amount");
        //SORTS TRANSACTIONS BY NEWEST TO OLDEST. HOLDS SORT WITHIN TIMESORTTRANSACTION
        ArrayList<Transaction> sorted = Transaction.loadTransactions();
        //
        for (Transaction timeSortTransaction : sorted) {
            System.out.println(timeSortTransaction);
        }
    }

    //DEPOSIT DISPLAY SCREEN checks if price is positive then only prints positive values
    public static void displayDeposits() {
        System.out.println("-Showing only Deposits-");
        System.out.println("Newest Entries Shown First");
        System.out.println("date | time | description | vendor | amount");
        ArrayList<Transaction> sorted = Transaction.loadTransactions();
        for (Transaction timeSortTransaction : sorted) {
            //positive check here
            if (timeSortTransaction.getPrice() > 0) {
                System.out.println(timeSortTransaction);
            }
        }
    }

    //PAYMENT DISPLAY SCREEN checks if price is negative then only prints negative values
    public static void displayPayments() {
        System.out.println("-Showing only Payments-");
        System.out.println("Newest Entries Shown First");
        System.out.println("date | time | description | vendor | amount");
        //SORTS AND HOLDS TRANSACTIONS IN TIMESORTTRANSACTION
        ArrayList<Transaction> sorted = Transaction.loadTransactions();
        for (Transaction timeSortTransaction : sorted) {
            //negative check here
            if (timeSortTransaction.getPrice() < 0) {
                System.out.println(timeSortTransaction);
            }
        }
    }

    //REPORTS SCREEN
    public static void displayReports() {
        while (true) {
            System.out.println("Welcome to the Reports Screen");
            System.out.println("Please choose from the following Report Options\n");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back - Return to the Ledger Screen\n");
            System.out.print("Please enter your Number Selection Here:  /");

            switch (scanInt()) {
                case 1: //DISPLAY MTD REPORT: DISPLAY TRANSACTIONS DURING MONTH, MONTH NET, AND TOTAL ACCOUNT BALANCE
                    System.out.println("Continue to Month to Date Report (Please press Enter):  ");
                    scanner.nextLine();
                    mtdReport();
                    break;
                case 2: //DISPLAY LAST MONTHS REPORT: DISPLAY TRANSACTIONS DURING LAST MONTH, THAT MONTHS NET CHANGE, AND TOTAL ACCOUNT BALANCE
                    System.out.println("Continue to Last Months MTD Report (Please press Enter):  ");
                    scanner.nextLine();
                    pmtdReport();
                    break;
                case 3: //DISPLAY YTD REPORT: DISPLAY TRANSACTIONS DURING YEAR, YEAR NET, AND TOTAL ACCOUNT BALANCE
                    System.out.println("Continue to Year to Date Report (Please press Enter):  ");
                    scanner.nextLine();
                    ytdReport();
                    break;
                case 4: //DISPLAY PREVIOUS YEARS REPORT: DISPLAY TRANSACTIONS DURING PREVIOUS YEAR, THAT YEARS TOTAL NET CHANGE, AND TOTAL ACCOUNT BALANCE
                    System.out.println("Continue to the Previous Year Report (Please press Enter): ");
                    scanner.nextLine();
                    pytdReport();
                    break;
                case 5: //VENDOR NAME SEARCH: COMPARES TRANSACTIONS.CSV WITH INPUTTED VENDOR NAME
                    System.out.println("Continue to the Vendor Search Screen (Please press Enter): ");
                    scanner.nextLine();
                    nameSearch();
                    break;
                case 0: //RETURNS TO LEDGER(PREVIOUS) PAGE
                    System.out.println("Return to the Ledger Screen (Please press Enter):  ");
                    scanner.nextLine();
                    displayLedger();
                    break;
                default:
                    System.out.println("Invalid option selected, Please try again\n");
            }
        }
    }
    public static void mtdReport() {
        float accountTotal = 0;
        float mtdTotal = 0;
        ArrayList<Transaction> sorted = ListParse.loadTransactions();
        LocalDateTime today = LocalDateTime.now();

        System.out.println("Month to Date Report: \n");
        for (Transaction timeSortTransaction : sorted) {
            //if time of transaction by month is the same as current month and the year is the same as the current year, collect values.
            if (timeSortTransaction.getNow().getMonth().equals(LocalDateTime.now().getMonth()) && timeSortTransaction.getNow().getYear() == today.getYear()) {
                //print sorted report list to screen
                System.out.println(timeSortTransaction);
                //calculate mtd total
                mtdTotal += timeSortTransaction.getPrice();
            }
            //outside of if, calculate account total
            accountTotal += timeSortTransaction.getPrice();

        }
        //prints calculated values here
        System.out.printf("Net Monthly Transactions: $%.2f\n", mtdTotal);
        System.out.printf("Account Total: $%.2f\n\n", accountTotal);
    }

    //PMTDREPORT: displays transactions from previous month, gives net account change for previous month and account total
    public static void pmtdReport() {
        float pmtdTotal = 0;
        float accountTotal = 0;
        ArrayList<Transaction> sorted = ListParse.loadTransactions();
        LocalDateTime today = LocalDateTime.now();

        //same idea as above but subtracting a month
        System.out.println("Previous Month to Date Report: \n");
        for (Transaction timeSortTransaction : sorted) {
            if (timeSortTransaction.getNow().getMonth().equals(today.getMonth().minus(1)) && timeSortTransaction.getNow().getYear() == today.getYear()){
                //print sorted transactions to screen
                System.out.println(timeSortTransaction);
                //calculate previous mtd total
                pmtdTotal += timeSortTransaction.getPrice();
            }
            //calculate account total
            accountTotal += timeSortTransaction.getPrice();
        }
        System.out.printf("Net Change of Previous Month: $%.2f\n\n", pmtdTotal);
        System.out.printf("Account Total: $%.2f\n\n", accountTotal);
    }

    //YTDREPORT: displays all transactions from this year, gives net account change for current year and account total
    public static void ytdReport() {
        float accountTotal = 0;
        float ytdTotal = 0;
        ArrayList<Transaction> sorted = ListParse.loadTransactions();
        System.out.println("Year to Date Report: \n");
        //within the sorted list
        for (Transaction timeSortTransaction : sorted) {
            //if transaction year is same as current year
            if (timeSortTransaction.getNow().getYear() == (LocalDateTime.now().getYear())) {
                //print list to screen
                System.out.println(timeSortTransaction);
                //ytd = all price entries added together from list
                ytdTotal += timeSortTransaction.getPrice();
            }
            //outside of if, add together all transactions in account
            accountTotal += timeSortTransaction.getPrice();
        }
        //prints calculated amounts
        System.out.printf("Net Yearly Transactions: $%.2f\n", ytdTotal);
        System.out.printf("Account Total: $%.2f\n", accountTotal);
    }

    //PYTDREPORT:  displays transactions from previous year, gives net account change for previous year and displays current account total
    public static void pytdReport() {
        float pytdTotal = 0;
        float accountTotal = 0;
        ArrayList<Transaction> sorted = ListParse.loadTransactions();
        LocalDateTime year = LocalDateTime.now();

        for (Transaction timeSortTransaction : sorted) {
            if (timeSortTransaction.getNow().getYear() == year.minusYears(1).getYear()) {
                System.out.println("Previous Year to Date Report: \n");
                System.out.println(timeSortTransaction);
                pytdTotal += timeSortTransaction.getPrice();
            }
            accountTotal += timeSortTransaction.getPrice();
        }
        System.out.printf("Net Change of Previous Year: $%.2f\n\n", pytdTotal);
        System.out.printf("Account Total: $%.2f\n\n", accountTotal);
    }

    //NAMESEARCH: TAKES INPUT NAME AND COMPARE TO NAME FROM LIST PARSE
    public static void nameSearch() {
        System.out.print("Please enter the source of the Transaction(vendor): ");
        String inputName = scanner.nextLine();
        System.out.printf("All transactions with Vendor Name matching: %s \n\n", inputName);
        ArrayList<Transaction> sorted = ListParse.loadTransactions();
        for (Transaction timeSortTransaction : sorted) {
            //NAME MATCH CHECK HERE
            if (timeSortTransaction.getVendor().equals(inputName)) {
                //PRINTS OUT TRANSACTIONS WITH MATCHING VENDOR NAME
                System.out.println(timeSortTransaction);
            }
        }
    }

    //SCANINT METHOD: ALLOWS TO CHOOSE FROM NUMBERS IN REPORTS SCREEN
    private static int scanInt() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Please ONLY enter an Integer");
            System.out.print("Enter number here: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
}
