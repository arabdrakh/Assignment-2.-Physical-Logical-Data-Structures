package banking.service;

import banking.models.BankAccount;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BankingService {
    private LinkedList<BankAccount> accounts;
    private Stack<String> transactionHistory;
    private Queue<String> billQueue;
    private Queue<String> accountRequests;

    public BankingService() {
        this.accounts = new LinkedList<>();
        this.transactionHistory = new Stack<>();
        this.billQueue = new LinkedList<>();
        this.accountRequests = new LinkedList<>();
    }

    // Abdrakhmanova Aruzhan 1
    public void addAccount(String accNum, String username, double balance) {
        accounts.add(new BankAccount(accNum, username, balance));
    }

    public List<BankAccount> getAllAccounts() {
        return accounts;
    }

    public BankAccount findAccountByUsername(String username) {
        for (BankAccount acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }

    // Abdrakhmanova Aruzhan 2
    public BankAccount depositMoney(String username, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        BankAccount acc = findAccountByUsername(username);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        acc.setBalance(acc.getBalance() + amount);
        addTransaction("Deposit " +  amount + " to " + username);
        return acc;
    }

    public BankAccount withdrawMoney(String username, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        BankAccount acc = findAccountByUsername(username);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (amount > acc.getBalance()) {
            throw new IllegalArgumentException(
                    "Insufficient funds. Current balance: " +  acc.getBalance());
        }
        acc.setBalance(acc.getBalance() - amount);
        addTransaction("Withdraw " + amount + " from " + username);
        return acc;
    }

    // Abdrakhmanova Aruzhan 3
    public void addTransaction(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction description cannot be empty.");
        }
        transactionHistory.push(description);
    }

    public String peekTransaction() {
        if (transactionHistory.isEmpty()) {
            return null;
        }
        return transactionHistory.peek();
    }

    public String undoTransaction() {
        if (transactionHistory.isEmpty()) {
            return null;
        }
        return transactionHistory.pop();
    }

    public Stack<String> getTransactionHistory() {
        return transactionHistory;
    }

    // Abdrakhmanova Aruzhan 4
    public void addBillPayment(String billName) {
        if (billName == null || billName.trim().isEmpty()) {
            throw new IllegalArgumentException("Bill name cannot be empty.");
        }
        billQueue.add(billName);
    }

    public String processNextBill() {
        if (billQueue.isEmpty()) {
            return null;
        }
        String processed = billQueue.poll();
        addTransaction("Bill payment: " + processed);
        return processed;
    }

    public String peekNextBill() {
        if (billQueue.isEmpty()) {
            return null;
        }
        return billQueue.peek();
    }

    public Queue<String> getBillQueue() {
        return billQueue;
    }

    // Abdrakhmanova Aruzhan 5
    public void submitAccountRequest(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        accountRequests.add(name);
    }

    public BankAccount processNextAccountRequest() {
        if (accountRequests.isEmpty()) {
            return null;
        }
        String name = accountRequests.poll();
        String accNum = "ACC" + (accounts.size() + 1001);
        BankAccount newAcc = new BankAccount(accNum, name, 0);
        accounts.add(newAcc);
        return newAcc;
    }

    public Queue<String> getPendingAccountRequests() {
        return accountRequests;
    }

    // Abdrakhmanova Aruzhan 6
    public BankAccount[] getAccountsArray() {
        BankAccount[] bankArray = new BankAccount[3];
        bankArray[0] = new BankAccount("ACC001", "Ali", 150000);
        bankArray[1] = new BankAccount("ACC002", "Sara", 220000);
        bankArray[2] = new BankAccount("ACC003", "Omar", 180000);
        return bankArray;
    }
}
