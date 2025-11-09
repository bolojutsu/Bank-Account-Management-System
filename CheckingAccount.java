public class CheckingAccount extends Account {
    private int transactionCount;
    private final double feePerTransaction = 0.50;
    private final int freeTransactionLimit = 5;
    private final double monthlyFee = 5.00;

    // Constructor for creating a checking account calls uses super to create the
    // account
    public CheckingAccount(double balance, String accountHolder, String accountType,
            int accountId) {
        super(balance, accountHolder, accountType, accountId);
    }

    // Overwriten method withdraw called when checking account is withdraing money
    // enables a tranaction fee if the number of transaction is greater than
    // transaction limit
    @Override
    public void withDraw(double amount) {
        if (transactionCount > freeTransactionLimit) {
            super.withDraw(feePerTransaction);
        }
        super.withDraw(amount);
        transactionCount++;
    }

    // Overwriten method called when checking account is depositing money
    // enables a tranaction fee if the number of transaction is greater than
    // transaction limit
    @Override
    public void transfer(Account toAccount, double amount) {
        if (transactionCount > freeTransactionLimit) {
            super.withDraw(feePerTransaction);
        }
        super.transfer(toAccount, amount);
        transactionCount++;
    }

    // Method used to withdraw money at the end of each month
    public void processMonthlyStatements() {
        super.withDraw(monthlyFee);
        transactionCount = 0;
    }
}
