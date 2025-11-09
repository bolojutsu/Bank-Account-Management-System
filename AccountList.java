public class AccountList<I extends Account> implements ListInterface<I> {
    private static final int INITIAL_ARRAY_SIZE = 5;
    private Object[] accountList = new Object[INITIAL_ARRAY_SIZE];
    private int numAccounts = 0;

    public AccountList() {
        accountList = new Object[INITIAL_ARRAY_SIZE];
        numAccounts = 0;
    }

    @Override
    public boolean isEmpty() {
        return (numAccounts == 0);
    }

    public boolean isFull() {
        return (numAccounts == accountList.length);
    }

    @Override
    public int size() {
        return numAccounts;
    }

    @Override
    public ListInterface<I> copy() {
        AccountList<I> accountsCopy = new AccountList<>();
        accountsCopy.accountList = new Object[this.accountList.length];
        for (int i = 0; i < numAccounts; i++) {
            accountsCopy.accountList[i] = accountList[i];
        }
        accountsCopy.numAccounts = numAccounts;
        return accountsCopy;
    }

    public void resize() {
        Object[] accountListCopy = new Object[this.accountList.length * 2];
        for (int i = 0; i < numAccounts; i++) {
            accountListCopy[i] = accountList[i];
        }
        accountList = accountListCopy;
    }

    @Override
    public void add(I account) {
        if (account == null) {
            throw new NullPointerException();
        }

        if (isFull()) {
            resize();
        }

        accountList[numAccounts] = account;
        numAccounts++;
    }

    @Override
    public void add(I account, int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > numAccounts) {
            throw new IndexOutOfBoundsException();
        }
        if (account == null) {
            throw new NullPointerException();
        }

        if (numAccounts == accountList.length) {
            resize();
        }

        if (this.isEmpty()) {
            this.add(account);
        } else {
            for (int i = numAccounts; i > index; i--) {
                this.accountList[i] = this.accountList[i - 1];
            }
            this.accountList[index] = account;
            this.numAccounts++;
        }
    }

    @Override
    public I get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= numAccounts) {
            throw new IndexOutOfBoundsException();
        }
        return (I) accountList[index];
    }

    @Override
    public I remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= numAccounts) {
            throw new IndexOutOfBoundsException();
        }
        I removedAccount = (I) accountList[index];

        if (numAccounts == 1) {
            removeAll();
        } else {
            for (int i = index; i < numAccounts - 1; i++) {
                accountList[i] = accountList[i + 1];
            }
            numAccounts--;
        }
        return removedAccount;
    }

    @Override
    public void removeAll() {
        accountList = new Object[INITIAL_ARRAY_SIZE];
        numAccounts = 0;
    }

    @Override
    public I replace(I account, int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= numAccounts) {
            throw new IndexOutOfBoundsException();
        }

        if (account == null) {
            throw new NullPointerException();
        }

        I oldAccount = (I) accountList[index];
        accountList[index] = account;
        return oldAccount;
    }

    public Account findById(int id) {
        for (int i = 0; i < numAccounts; i++) {
            Account account = (Account) accountList[i];
            if (account.getAccountId() == id) {
                return account;
            }
        }
        return null;
    }

}
