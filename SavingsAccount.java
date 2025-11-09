public class SavingsAccount extends Account {
    private final double intrestRate = 0.02;
    private final double minBalanceRequired = 500;
    private final double lowBalanceFee = 10;
    private final int maxWithdrawals = 6;
    private int currentWithdrawals;

    public SavingsAccount(double balance, String accountHolder, String accountType, int accountId) {
        super(balance, accountHolder, accountType, accountId);
    }

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

    public void appyIntrest() {
        double interest = this.getBalance() * (intrestRate / 12);
        super.deposit(interest);
    }

    public void resetWithdrawalCount() {
        currentWithdrawals = 0;
    }

}
