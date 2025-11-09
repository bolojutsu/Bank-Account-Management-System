import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Account implements Comparable<Account> {
    protected static final Set<String> usedAccountNumbers = new HashSet<>();
    private double balance;
    private String accountNumber;
    private String accountHolder;
    private String accountType;
    private int accountId;

    public Account() {
        this.accountNumber = "000";
        this.accountHolder = "UN";
        this.accountType = "UN";
        this.accountId = 0;
    }

    public Account(double balance, String accountHolder, String accountType, int accountId) {
        if (balance < 0) {
            throw new IllegalArgumentException("Inital balance cannot be negative");
        }
        this.balance = balance;
        this.accountHolder = accountHolder;
        if (!isValidAccountType(accountType)) {
            throw new IllegalArgumentException("Invalid account type: must be checking or savings");
        }
        this.accountType = accountType.toLowerCase();
        this.accountId++;
        generateAccountNumber();
    }

    private boolean isValidAccountType(String type) {
        return type.equalsIgnoreCase("checking") || type.equalsIgnoreCase("savings");
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            this.balance = 0.00;
        }
    }

    public void generateAccountNumber() {
        if (usedAccountNumbers.size() >= 1000) {
            throw new IllegalStateException("All possible account numbers (0-999) have been used.");
        }

        Random randomNumber = new Random();
        String generatedNumber;

        do {
            int randomInt = randomNumber.nextInt(1000);
            generatedNumber = String.format("%03d", randomInt);
        } while (usedAccountNumbers.contains(generatedNumber));

        this.accountNumber = generatedNumber;
        usedAccountNumbers.add(generatedNumber);
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // Added for loading from file; keeps usedAccountNumbers consistent
    public void setAccountNumbers(String newAccountNumber) {
        if (newAccountNumber == null || newAccountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (accountNumber != null) {
            usedAccountNumbers.remove(accountNumber);
        }
        if (usedAccountNumbers.contains(newAccountNumber)) {
            throw new IllegalArgumentException("Duplicate account number: " + newAccountNumber);
        }
        this.accountNumber = newAccountNumber;
        usedAccountNumbers.add(newAccountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return Integer.compare(this.accountId, other.accountId);
    }

    public void display() {
        System.out.println("Account: " + accountNumber + " | Owner: " + accountHolder + "| Balance: $" + balance);
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amont must be positive");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount + ". New balance: $ " + balance);
    }

    public void withDraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("withdraw amount must be positive");
        }

        if (amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        System.out.println("Withdrew: $ " + amount + "New balance: $" + balance);
    }

    public void transfer(Account toAccount, double amount) {
        withDraw(amount);
        toAccount.deposit(amount);
    }

}