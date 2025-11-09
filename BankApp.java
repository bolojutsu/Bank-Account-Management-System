import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = bank.getScanner();
        System.out.println("Welcome to simple bank");
        String filename = "Accounts.txt";
        boolean running = true;

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Delete Accounts");
            System.out.println("7. Save Accounts");
            System.out.println("8. Load Accounts");
            System.out.println("9. Exit");

            System.out.print("Choose an option: ");
            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.deposit();
                    break;
                case 3:
                    bank.withDraw();
                    break;
                case 4:
                    bank.transfer();
                    break;
                case 5:
                    bank.displayAll();
                    break;
                case 6:
                    bank.deleteAccount();
                    break;
                case 7:
                    bank.saveToFile(filename);
                    break;
                case 8:
                    bank.loadFromFile(filename);
                    break;
                case 9:
                    running = false;
                    System.out.println("thanks for banking with us");
                    break;

                default:
                    System.out.println("Invalid option. try again");
            }
        }
        scanner.close();
    }
}
