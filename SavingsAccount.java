public class SavingsAccount extends Account {
    private final double intrestRate = 0.02;
    private final double minBalanceRequired = 500;
    private final double lowBalanceFee = 10;
    private final int maxWithdrawals = 6;
    private int currentWithdrawals;

    // contrustor used for creating a savings account, uses super to create account
    public SavingsAccount(double balance, String accountHolder, String accountType, int accountId) {
        super(balance, accountHolder, accountType, accountId);
    }

    // Overwritten method used when trying to withdraw from the saving account
    // if withdrawss limit is reached throw IlegalStateException
    // else withdraw amount
    @Override
    public void withDraw(double amount) {
        if (currentWithdrawals >= maxWithdrawals) {
            throw new IllegalStateException("Withdraw limit reached for the month");
        }

        super.withDraw(amount);

        if (this.getBalance() < minBalanceRequired) {
            super.withDraw(lowBalanceFee);
        }
        currentWithdrawals++;
    }

    @Override
    public void transfer(Account toAccount, double amount) {
        super.transfer(toAccount, amount);
        currentWithdrawals++;
    }

    // Method allows user to deposit more monet into savings account
    @Override
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        super.deposit(amount);
    }

    // Method used to apply intrest to the balance stored in savings account at the
    // end of the month
    public void appyIntrest() {
        double interest = this.getBalance() * (intrestRate / 12);
        super.deposit(interest);
        System.out.println("Intrest added to balance");
    }

    // Method used to reset withdraw amount at the end of the month
    public void resetWithdrawalCount() {
        currentWithdrawals = 0;
    }

}
