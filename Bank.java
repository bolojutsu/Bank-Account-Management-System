import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Bank {
    private final AccountList<Account> accounts;
    private final Scanner scanner;
    private int nextAccountId = 1;

    public Bank() {
        accounts = new AccountList<>();
        scanner = new Scanner(System.in);
    }

    // This method is used for creating an account: User chooses between checking.
    public void createAccount() {
        System.out.println("Enter Account Holder name: ");
        String holder = scanner.nextLine();

        System.out.println("Enter initial balance: $");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter account type (checking/savings)");

        String type = scanner.nextLine().toLowerCase();
        Account newAccount = null;

        try {
            if (type.equals("checking")) {
                newAccount = new CheckingAccount(initialBalance, holder, type, nextAccountId);
            } else if (type.equals("savings")) {
                newAccount = new SavingsAccount(initialBalance, holder, type, nextAccountId);
            } else {
                throw new IllegalArgumentException("Invalid account type: must be checking or savings");
            }
            accounts.add(newAccount);
            System.out.printf("Account created! ID: %d, Number: %s,", newAccount.getAccountId(),
                    newAccount.getAccountNumber());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Helper method: used to find account by the Unique given Account Number.
    private Account findAccountByNumber(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Method calls the method deposit from Account class. If account does not exist
    // throw NullPointerException
    // else deposit amount
    public void deposit() {
        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            throw new NullPointerException("Account does not exist");
        }
        System.out.println("Enter deposit amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            account.deposit(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method calls the withdraw method from Account class allowing money to be
    // withdrawn from the specified Account number throws NullPointerException if
    // account does not exist
    public void withDraw() {
        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            throw new NullPointerException("Account does not exist");
        }
        System.out.println("Enter withdraw amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            account.withDraw(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method calls transfer method from Account class allowing the user to transfer
    // money from one account to another if either account does not exist throw
    // NullPointerException
    public void transfer() {
        System.out.println("Enter from account number");
        String fromNumber = scanner.nextLine();
        Account fromAccount = findAccountByNumber(fromNumber);
        if (fromAccount == null) {
            throw new NullPointerException("Account does not exist");
        }

        System.out.println("Enter to accont number: ");
        String toNumber = scanner.nextLine();
        Account toAccount = findAccountByNumber(toNumber);
        if (toAccount == null) {
            throw new NullPointerException("Account does not exist");
        }

        System.out.println("Enter transfer amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            fromAccount.transfer(toAccount, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    // Method used to display all accounts that are currently listed in the list of
    // Accounts
    public void displayAll() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts yet");
        }
        System.out.println("\n--- All Accounts ---");
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).display();
        }
        System.out.println("-----------------------\n");
    }

    public Scanner getScanner() {
        return scanner;
    }

    // Method used to procces satements for each account at the end of the mounth
    // checks if account is checking or saving and calls respective method for
    // processing statements
    public void processEndOfMonth() {
        System.out.println("\n--- Running End-Of-Month Processing");
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);

            if (account instanceof CheckingAccount) {
                ((CheckingAccount) account).processMonthlyStatements();
            } else if (account instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) account;
                savings.appyIntrest();
                savings.resetWithdrawalCount();
            }
        }
        System.out.println("Monthly processing complete. Balance updated");
    }

    // Method allow an account to be removed based off the account number
    // if account to be deleted does not exist throw NullPointerException
    // else deletes account from list from memory and acount number from hashset
    public void deleteAccount() {
        System.out.println("--- Delete Account ---");
        System.out.println("Enter Account Number to close");
        String accountNumber = scanner.nextLine();

        Account accountToRemove = findAccountByNumber(accountNumber);

        if (accountToRemove == null) {
            throw new NullPointerException("Account does not exist");
        }

        int indexToRemove = -1;
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account == accountToRemove) {
                indexToRemove = i;
                break;
            }
        }

        try {
            if (indexToRemove != -1) {
                accounts.remove(indexToRemove);
                Account.usedAccountNumbers.remove(accountNumber);
                System.out.println("Success: Account " + accountNumber + "For " + accountToRemove.getAccountHolder()
                        + " has been cloded and deleted from memory");
                System.out.println("Reminder: Use option 7 to save this change to file.");
            } else {
                System.out.println("Internal Error: Account found by number, but index was not located");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Internal Error: Could not remove account at index " + indexToRemove + ".");
        }
    }

    // Method used to save all accounts created to a csv file for data keeping
    public void saveToFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println("AccountId, AccountHolder, Balance, AccountNumber");
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                writer.println(account.getAccountId() + "," + account.getAccountHolder() + ","
                        + account.getAccountType() + "," + account.getBalance() + "," + account.getAccountNumber());
            }
            System.out.println("Accounts saved to " + filename + " (" + accounts.size() + " accounts)");
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Method used to load all existing accounts for any kind of processing
    public void loadFromFile(String filename) {
        accounts.removeAll();
        nextAccountId = 1;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue;
                }
                int id = Integer.parseInt(parts[0].trim());
                String holder = parts[1].trim();
                String type = parts[2].trim().toLowerCase();
                double balance = Double.parseDouble(parts[3].trim());
                String accountNumber = parts[4].trim();
                Account loadedAccount;

                if (type.equals("checking")) {
                    loadedAccount = new CheckingAccount(balance, holder, type, id);
                } else if (type.equals("savings")) {
                    loadedAccount = new SavingsAccount(balance, holder, type, id);
                } else {
                    continue;
                }

                loadedAccount.setAccountNumbers(accountNumber);
                accounts.add(loadedAccount);
                Account.usedAccountNumbers.add(accountNumber);
                if (id >= nextAccountId) {
                    nextAccountId = id + 1;
                }
            }
            System.out.println("loaded " + accounts.size() + " Accounts from " + filename + ".");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
