public class CheckingAccount extends Account {
    private int transactionCount;
    private final double feePerTransaction = 0.50;
    private final int freeTransactionLimit = 5;
    private final double monthlyFee = 5.00;

    public CheckingAccount(double balance, String accountHolder, String accountType,
            int accountId) {
        super(balance, accountHolder, accountType, accountId);
    }

    @Override
    public void withDraw(double amount) {
        if (transactionCount >= freeTransactionLimit) {
            super.withDraw(feePerTransaction);
        }
        super.withDraw(amount);
        transactionCount++;
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        if (transactionCount > freeTransactionLimit) {
            super.withDraw(feePerTransaction);
        }
        super.transfer(toAccount, amount);
        transactionCount++;
    }

    public void processMonthlyStatements() {
        super.withDraw(monthlyFee);
        transactionCount = 0;
    }

}
