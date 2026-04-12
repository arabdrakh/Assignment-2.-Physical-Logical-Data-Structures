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
            System.out.println("  BANKING SYSTEM — Main Menu");
            System.out.println(" ");
            System.out.println("1 — Part 1: Logical Data Structures");
            System.out.println("2 — Part 2: Physical Data Structures");
            System.out.println("3 — Part 3: Mini Banking Menu");
            System.out.println("0 — Exit");
            System.out.print("Choose: ");

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

    private void runPart1() {
        while (true) {
            System.out.println("\nPart 1: Logical Data Structures");
            System.out.println("1 — Task 1: Bank Account Storage");
            System.out.println("2 — Task 2: Deposit & Withdraw");
            System.out.println("3 — Task 3: Transaction History");
            System.out.println("4 — Task 4: Bill Payment Queue");
            System.out.println("5 — Task 5: Account Opening Queue");
            System.out.println("0 — Back");
            System.out.print("Choose task: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> task1Menu();
                case "2" -> task2Menu();
                case "3" -> task3Menu();
                case "4" -> task4Menu();
                case "5" -> task5Menu();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void task1Menu() {
        while (true) {
            System.out.println("\nTask 1: Bank Account Storage");
            System.out.println("1 — Add a new account");
            System.out.println("2 — Display all accounts");
            System.out.println("3 — Search account by username");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addAccount();
                case "2" -> displayAllAccounts();
                case "3" -> searchByUsername();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

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
            System.out.println("Invalid balance");
            return;
        }
        service.addAccount(accNum, username, balance);
        System.out.println("Account added successfully");
    }

    private void displayAllAccounts() {
        List<BankAccount> accounts = service.getAllAccounts();
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

    private void searchByUsername() {
        System.out.print("Enter username to search: ");
        String name = scanner.nextLine().trim();
        BankAccount acc = service.findAccountByUsername(name);
        
        if (acc != null) {
            System.out.println("Found: " + acc);
        } else {
            System.out.println("Account not found for username: " + name);
        }
    }

    private void task2Menu() {
        while (true) {
            System.out.println("\nTask 2: Deposit & Withdraw");
            System.out.println("1 — Deposit money");
            System.out.println("2 — Withdraw money");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> depositMoney();
                case "2" -> withdrawMoney();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
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
            System.out.println("Invalid amount.");
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
            System.out.println("Invalid amount.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void task3Menu() {
        while (true) {
            System.out.println("\nTask 3: Transaction History");
            System.out.println("1 — Add transaction");
            System.out.println("2 — Display last transaction (peek)");
            System.out.println("3 — Undo last transaction (pop)");
            System.out.println("4 — Display all transactions");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addTransaction();
                case "2" -> peekTransaction();
                case "3" -> undoTransaction();
                case "4" -> displayAllTransactions();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
        }
    }

    private void addTransaction() {
        System.out.println("Transaction types: Deposit, Withdraw, Bill payment");
        System.out.print("Enter transaction description: ");
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
        System.out.println();
    }

    private void task4Menu() {
        while (true) {
            System.out.println("\nTask 4: Bill Payment Queue");
            System.out.println("1 — Add bill payment request");
            System.out.println("2 — Process next bill payment");
            System.out.println("3 — Display queue");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addBillPayment();
                case "2" -> processNextBill();
                case "3" -> displayBillQueue();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
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
            System.out.println("Last transaction: " + transactionHistory.peek() + "\n");
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

    private void task5Menu() {
        while (true) {
            System.out.println("\nTask 5: Account Opening Queue");
            System.out.println("1 — Submit account request");
            System.out.println("2 — Process next request (Admin)");
            System.out.println("3 — Display pending requests");
            System.out.println("0 — Back");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> submitAccountRequest();
                case "2" -> processAccountRequest();
                case "3" -> displayPendingRequests();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid");
            }
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

    private void runPart2() {
        System.out.println("\n Part 2 / Task 6: Physical Data Structures (Array)");

        BankAccount[] bankArray = service.getPredefinedAccountsArray();

        for (int i = 0; i < fixedAccounts.length; i++) {
            System.out.println((i + 1) + ". " + fixedAccounts[i]);
        }
        System.out.println();
    }

    private void runPart3() {
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

    private void bankMenu() {
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

    private void atmMenu() {
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

    private void adminMenu() {
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
