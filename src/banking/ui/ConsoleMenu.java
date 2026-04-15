package banking.ui;

import banking.models.BankAccount;
import banking.service.BankingService;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class ConsoleMenu {
    private final BankingService service;
    private final Scanner scanner;

    public ConsoleMenu(BankingService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n  MINI BANKING SYSTEM");
            System.out.println("1 — Enter Bank");
            System.out.println("2 — Enter ATM");
            System.out.println("3 — Admin Area");
            System.out.println("0 — Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> bankMenu();
                case "2" -> atmMenu();
                case "3" -> adminMenu();
                case "0" -> {
                    System.out.println("thx");
                    return;
                }
                default -> System.out.println("no");
            }
        }
    }

    private void bankMenu() {
        while (true) {
            System.out.println("\n Bank Menu");
            System.out.println("1 — [Task 1] Add a new account directly");
            System.out.println("2 — [Task 2] Deposit money");
            System.out.println("3 — [Task 2] Withdraw money");
            System.out.println("4 — [Task 3] Add custom transaction");
            System.out.println("5 — [Task 4] Add bill payment request");
            System.out.println("6 — [Task 5] Submit account opening request");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addAccount();
                case "2" -> depositMoney();
                case "3" -> withdrawMoney();
                case "4" -> addTransaction();
                case "5" -> addBillPayment();
                case "6" -> submitAccountRequest();
                case "0" -> { return; }
                default -> System.out.println("no");
            }
        }
    }

    private void atmMenu() {
        while (true) {
            System.out.println("\n ATM Menu");
            System.out.println("1 — [Task 1] Balance enquiry");
            System.out.println("2 — [Task 2] Withdraw money");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> balanceEnquiry();
                case "2" -> withdrawMoney();
                case "0" -> { return; }
                default -> System.out.println("no");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n Admin Area");
            System.out.println("1 — [Task 1] Display all accounts");
            System.out.println("2 — [Task 3] Display transaction history");
            System.out.println("3 — [Task 3] Peek last transaction");
            System.out.println("4 — [Task 3] Undo last transaction");
            System.out.println("5 — [Task 4] Display bill payment queue");
            System.out.println("6 — [Task 4] Process next bill payment");
            System.out.println("7 — [Task 5] Display pending account requests");
            System.out.println("8 — [Task 5] Process next account request");
            System.out.println("9 — [Task 6] View predefined accounts array");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> displayAllAccounts();
                case "2" -> displayAllTransactions();
                case "3" -> peekTransaction();
                case "4" -> undoTransaction();
                case "5" -> displayBillQueue();
                case "6" -> processNextBill();
                case "7" -> displayPendingRequests();
                case "8" -> processAccountRequest();
                case "9" -> displayPredefinedAccounts();
                case "0" -> { return; }
                default -> System.out.println("no");
            }
        }
    }

    // --- Task Implementations ---

    private void addAccount() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter initial balance: ");
        double balance;
        try {
            balance = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("no");
            return;
        }
        service.addAccount(accNum, username, balance);
        System.out.println("Account added");
    }

    private void displayAllAccounts() {
        List<BankAccount> accounts = service.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts");
            return;
        }
        System.out.println("Accounts:");
        int i = 1;
        for (BankAccount acc : accounts) {
            System.out.println(i + ". " + acc);
            i++;
        }
    }

    private void depositMoney() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Deposit: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
            BankAccount acc = service.depositMoney(username, amount);
            System.out.println("New balance: " + String.format("%.0f", acc.getBalance()));
        } catch (NumberFormatException e) {
            System.out.println("no");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void withdrawMoney() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Withdraw: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
            BankAccount acc = service.withdrawMoney(username, amount);
            System.out.println("New balance: " + String.format("%.0f", acc.getBalance()));
        } catch (NumberFormatException e) {
            System.out.println("no");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addTransaction() {
        System.out.println("Transaction types: Deposit, Withdraw, Bill payment");
        System.out.print("Enter transaction: ");
        String desc = scanner.nextLine().trim();
        try {
            service.addTransaction(desc);
            System.out.println("Transaction added: " + desc);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void peekTransaction() {
        String last = service.peekTransaction();
        if (last == null) {
            System.out.println("No transactions in history.");
        } else {
            System.out.println("Last transaction: " + last);
        }
    }

    private void undoTransaction() {
        String removed = service.undoTransaction();
        if (removed == null) {
            System.out.println("No transactions to undo.");
        } else {
            System.out.println("Undo → " + removed + " removed");
        }
    }

    private void displayAllTransactions() {
        Stack<String> history = service.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions in history.");
            return;
        }
        System.out.println("Transaction History (most recent first):");
        for (int i = history.size() - 1; i >= 0; i--) {
            System.out.println("  " + (history.size() - i) + ". " + history.get(i));
        }
    }

    private void addBillPayment() {
        System.out.print("Enter bill name (e.g. Electricity Bill): ");
        String bill = scanner.nextLine().trim();
        try {
            service.addBillPayment(bill);
            System.out.println("Added: " + bill);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processNextBill() {
        String processed = service.processNextBill();
        if (processed == null) {
            System.out.println("No bills in the queue.");
            return;
        }
        System.out.println("Processing: " + processed);
        
        String remaining = service.peekNextBill();
        if (remaining != null) {
            System.out.println("Remaining: " + remaining);
        } else {
            System.out.println("No more bills remaining.");
        }
    }

    private void displayBillQueue() {
        Queue<String> queue = service.getBillQueue();
        if (queue.isEmpty()) {
            System.out.println("Bill queue is empty.");
            return;
        }
        System.out.println("Bill Payment Queue:");
        int i = 1;
        for (String bill : queue) {
            System.out.println("  " + i + ". " + bill);
            i++;
        }
    }

    private void submitAccountRequest() {
        System.out.print("Enter your name for account request: ");
        String name = scanner.nextLine().trim();
        try {
            service.submitAccountRequest(name);
            System.out.println("Request submitted for: " + name);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processAccountRequest() {
        BankAccount acc = service.processNextAccountRequest();
        if (acc == null) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("Request processed! Account created for " + acc.getUsername());
        System.out.println("  Account Number: " + acc.getAccountNumber() + ", Balance: 0");
    }

    private void displayPendingRequests() {
        Queue<String> requests = service.getPendingAccountRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("Pending Account Requests:");
        int i = 1;
        for (String req : requests) {
            System.out.println("  " + i + ". " + req);
            i++;
        }
    }

    private void balanceEnquiry() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        BankAccount acc = service.findAccountByUsername(username);
        
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Account: " + acc.getUsername());
        System.out.println("Balance: " + String.format("%.0f", acc.getBalance()));
    }

    private void displayPredefinedAccounts() {
        System.out.println("\n Task 6: Physical Data Structures (Array)");
        BankAccount[] bankArray = service.getPredefinedAccountsArray();

        System.out.println("Predefined accounts stored in array BankAccount[3]:");
        for (int i = 0; i < bankArray.length; i++) {
            System.out.println((i + 1) + ". " + bankArray[i]);
        }
    }
}
