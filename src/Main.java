import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    // ===== Shared data structures =====
    static LinkedList<BankAccount> accounts = new LinkedList<>();
    static Stack<String> transactionHistory = new Stack<>();
    static Queue<String> billQueue = new LinkedList<>();
    static Queue<String> accountRequests = new LinkedList<>();

    static Scanner scanner = new Scanner(System.in);

    // =====================================================================
    // PART 1 – LOGICAL DATA STRUCTURES
    // =====================================================================

    // --------------- Task 1 – Bank Account Storage (LinkedList) ---------------

    static void addAccount() {
        System.out.print("Account number: ");
        String number = scanner.nextLine();
        System.out.print("Username: ");
        String name = scanner.nextLine();
        System.out.print("Initial balance: ");
        double balance = Double.parseDouble(scanner.nextLine());
        accounts.add(new BankAccount(number, name, balance));
        System.out.println("Account added\n");
    }

    static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.\n");
            return;
        }
        System.out.println("Accounts List:");
        int index = 1;
        for (BankAccount acc : accounts) {
            System.out.println(index + ". " + acc);
            index++;
        }
        System.out.println();
    }

    static BankAccount searchByUsername(String username) {
        for (BankAccount acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }

    static void searchAccountMenu() {
        System.out.print("Enter username to search: ");
        String name = scanner.nextLine();
        BankAccount found = searchByUsername(name);
        if (found != null) {
            System.out.println("Found: " + found + "\n");
        } else {
            System.out.println("Account not found.\n");
        }
    }

    // --------------- Task 2 – Deposit & Withdraw ---------------

    static void deposit() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        BankAccount acc = searchByUsername(name);
        if (acc == null) {
            System.out.println("Account not found.\n");
            return;
        }
        System.out.print("Deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0) {
            System.out.println("Invalid amount.\n");
            return;
        }
        acc.setBalance(acc.getBalance() + amount);
        // Task 3 – record to stack
        transactionHistory.push("Deposit " + (int) amount + " to " + name);
        System.out.println("New balance: " + (int) acc.getBalance() + "\n");
    }

    static void withdraw() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        BankAccount acc = searchByUsername(name);
        if (acc == null) {
            System.out.println("Account not found.\n");
            return;
        }
        System.out.print("Withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount <= 0 || amount > acc.getBalance()) {
            System.out.println("Insufficient funds or invalid amount.\n");
            return;
        }
        acc.setBalance(acc.getBalance() - amount);
        transactionHistory.push("Withdraw " + (int) amount + " from " + name);
        System.out.println("New balance: " + (int) acc.getBalance() + "\n");
    }

    // --------------- Task 3 – Transaction History (Stack – LIFO) ---------------

    static void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.\n");
            return;
        }
        System.out.println("Transaction History (most recent first)");
        for (int i = transactionHistory.size() - 1; i >= 0; i--) {
            System.out.println("  " + transactionHistory.get(i));
        }
        System.out.println();
    }

    static void peekLastTransaction() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.\n");
        } else {
            System.out.println("Last transaction: " + transactionHistory.peek() + "\n");
        }
    }

    static void undoLastTransaction() {
        if (transactionHistory.isEmpty()) {
            System.out.println("Nothing to undo.\n");
        } else {
            String removed = transactionHistory.pop();
            System.out.println("Undo → " + removed + " removed\n");
        }
    }

    // --------------- Task 4 – Bill Payment Queue (Queue – FIFO) ---------------

    static void addBillPayment() {
        System.out.print("Enter bill name: ");
        String bill = scanner.nextLine();
        billQueue.add(bill);
        System.out.println("Added: " + bill + "\n");
    }

    static void processNextBill() {
        if (billQueue.isEmpty()) {
            System.out.println("No bills in queue.\n");
        } else {
            String bill = billQueue.poll();
            System.out.println("Processing: " + bill);
            transactionHistory.push("Bill payment: " + bill);
            if (billQueue.isEmpty()) {
                System.out.println("No remaining bills.\n");
            } else {
                System.out.println("Remaining: " + billQueue + "\n");
            }
        }
    }

    static void displayBillQueue() {
        if (billQueue.isEmpty()) {
            System.out.println("Bill queue is empty.\n");
        } else {
            System.out.println("Bill Queue: " + billQueue + "\n");
        }
    }

    // --------------- Task 5 – Account Opening Queue ---------------

    static void submitAccountRequest() {
        System.out.print("Enter name for new account request: ");
        String name = scanner.nextLine();
        accountRequests.add(name);
        System.out.println("Request submitted for: " + name + "\n");
    }

    static void processAccountRequest() {
        if (accountRequests.isEmpty()) {
            System.out.println("No pending requests.\n");
            return;
        }
        String name = accountRequests.poll();
        String accNumber = "ACC" + (accounts.size() + 1);
        accounts.add(new BankAccount(accNumber, name, 0));
        System.out.println("Approved: " + name + " → Account " + accNumber + " created with balance 0");
        System.out.println("Pending requests: " + (accountRequests.isEmpty() ? "none" : accountRequests) + "\n");
    }

    static void displayPendingRequests() {
        if (accountRequests.isEmpty()) {
            System.out.println("No pending requests.\n");
        } else {
            System.out.println("Pending requests: " + accountRequests + "\n");
        }
    }

    // =====================================================================
    // PART 2 – PHYSICAL DATA STRUCTURES (Task 6)
    // =====================================================================

    static void task6PhysicalArray() {
        System.out.println("=== Task 6: Physical Data Structure (Array) ===");
        BankAccount[] fixedAccounts = new BankAccount[3];
        fixedAccounts[0] = new BankAccount("001", "Ali", 150000);
        fixedAccounts[1] = new BankAccount("002", "Sara", 220000);
        fixedAccounts[2] = new BankAccount("003", "Omar", 310000);

        for (int i = 0; i < fixedAccounts.length; i++) {
            System.out.println((i + 1) + ". " + fixedAccounts[i]);
        }
        System.out.println();
    }

    // =====================================================================
    // PART 3 – MINI BANKING MENU
    // =====================================================================

    static void bankMenu() {
        while (true) {
            System.out.println("BANK MENU");
            System.out.println("1 – Add new account");          // Task 1
            System.out.println("2 – Display all accounts");     // Task 1
            System.out.println("3 – Search account");           // Task 1
            System.out.println("4 – Deposit money");            // Task 2
            System.out.println("5 – Withdraw money");           // Task 2
            System.out.println("6 – Transaction history");      // Task 3
            System.out.println("7 – Add bill payment");         // Task 4
            System.out.println("8 – Submit account request");   // Task 5
            System.out.println("0 – Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addAccount();
                    break;
                case "2":
                    displayAllAccounts();
                    break;
                case "3":
                    searchAccountMenu();
                    break;
                case "4":
                    deposit();
                    break;
                case "5":
                    withdraw();
                    break;
                case "6":
                    peekLastTransaction();
                    showTransactionHistory();
                    break;
                case "7":
                    addBillPayment();
                    break;
                case "8":
                    submitAccountRequest();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    // --- ATM Menu ---
    static void atmMenu() {
        while (true) {
            System.out.println("ATM MENU");
            System.out.println("1 – Balance enquiry");
            System.out.println("2 – Withdraw");
            System.out.println("0 – Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter username: ");
                    String name = scanner.nextLine();
                    BankAccount acc = searchByUsername(name);
                    if (acc != null) {
                        System.out.println("Balance: " + (int) acc.getBalance() + "\n");
                    } else {
                        System.out.println("Account not found.\n");
                    }
                    break;
                case "2":
                    withdraw();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    // --- Admin Menu ---
    static void adminMenu() {
        while (true) {
            System.out.println("ADMIN MENU");
            System.out.println("1 – View pending account requests");
            System.out.println("2 – Process next account request");
            System.out.println("3 – View bill payment queue");
            System.out.println("4 – Process next bill payment");
            System.out.println("5 – Undo last transaction");
            System.out.println("0 – Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayPendingRequests();
                    break;
                case "2":
                    processAccountRequest();
                    break;
                case "3":
                    displayBillQueue();
                    break;
                case "4":
                    processNextBill();
                    break;
                case "5":
                    undoLastTransaction();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.\n");
            }
        }
    }

    // =====================================================================
    // MAIN – Top-level menu
    // =====================================================================
    public static void main(String[] args) {

        // --- Run Task 6 once at startup to demonstrate the physical array ---
        task6PhysicalArray();

        // --- Pre-load sample accounts so the demo is immediately usable ---
        accounts.add(new BankAccount("001", "Ali", 150000));
        accounts.add(new BankAccount("002", "Sara", 220000));

        while (true) {
            System.out.println("       WELCOME TO BANKING SYSTEM       ");
            System.out.println("1 – Enter Bank");
            System.out.println("2 – Enter ATM");
            System.out.println("3 – Admin Area");
            System.out.println("4 – Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    bankMenu();
                    break;
                case "2":
                    atmMenu();
                    break;
                case "3":
                    adminMenu();
                    break;
                case "4":
                    System.out.println("Thank you for using Banking System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}
