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

    // Default constructor use to create a standard account
    public Account() {
        this.accountNumber = "000";
        this.accountHolder = "UN";
        this.accountType = "UN";
        this.accountId = 0;
    }

    // Overloaded Constructor when creating a account
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
        this.accountId = accountId;
        generateAccountNumber();
    }

    // Private helper method used to check if the type of account user enters is
    // valid (Checking/Savings)
    private boolean isValidAccountType(String type) {
        return type.equalsIgnoreCase("checking") || type.equalsIgnoreCase("savings");
    }

    // Getter methods for getting balance, account number, account holder, account
    // type, account ID
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

    // Setter methods for setting balance and account holder
    public void setBalance(double balance) {
        if (balance < 0) {
            this.balance = 0.00;
        }
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // Method used for generating unique random Account numbers ranging from
    // (000-999)
    // Once Account Number is generated the number is stored in a hashset
    // Account numbers cannot be duplicated
    // Should only be called when an account is created
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

    // Method used for depositing money into account
    // Takes in a double parameter amount
    // if amount is less than 0 throw IllegalArgumentException
    // else update the balance
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amont must be positive");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount + ". New balance: $ " + balance);
    }

    // Method used for withdrawing money from account
    // Takes in a double parameter amount
    // if amount is less than 0 or greater than balance throw
    // IllegalArgumentException
    // else update balance
    public void withDraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("withdraw amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Withdraw Amount is more than balance");
        }
        balance -= amount;
        System.out.println("Withdrew: $ " + amount + "New balance: $" + balance);
    }

    // Method used for transfering money from one account to another
    // Takes in two parameters Account and Double
    public void transfer(Account toAccount, double amount) {
        withDraw(amount);
        toAccount.deposit(amount);
    }
}