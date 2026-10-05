/**
 * Lớp trừu tượng biểu diễn tài khoản ngân hàng.
 *
 * Class Invariant:
 * balance >= 0
 */
public abstract class BankAccount {

    private String accountNumber;
    private String holderName;
    private double balance;

    /**
     * Khởi tạo tài khoản ngân hàng.
     *
     * @param accountNumber số tài khoản
     * @param holderName tên chủ tài khoản
     * @param balance số dư ban đầu
     */
    public BankAccount(String accountNumber, String holderName, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Số dư ban đầu không được âm!"
            );
        }

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException(
                    "Số dư không được âm!"
            );
        }

        this.balance = balance;
    }

    /**
     * Nạp tiền vào tài khoản.
     *
     * Pre-condition:
     * amount > 0
     *
     * Post-condition:
     * balance_new = balance_old + amount
     *
     * Invariant:
     * balance_new >= 0
     *
     * @param amount số tiền cần nạp
     * @throws InvalidAmountException nếu amount <= 0
     */
    public void deposit(double amount)
            throws InvalidAmountException {

        // Pre-condition
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Số tiền nạp phải lớn hơn 0!"
            );
        }

        double oldBalance = balance;

        balance += amount;

        // Post-condition
        if (balance != oldBalance + amount) {
            throw new IllegalStateException(
                    "Vi phạm hậu điều kiện của deposit!"
            );
        }

        // Class invariant
        if (balance < 0) {
            throw new IllegalStateException(
                    "Vi phạm bất biến: số dư không được âm!"
            );
        }
    }

    /**
     * Rút tiền khỏi tài khoản ngân hàng.
     *
     * Pre-condition:
     * amount > 0
     * balance >= amount
     *
     * Post-condition:
     * balance_new = balance_old - amount
     *
     * Invariant:
     * balance_new >= 0
     *
     * @param amount số tiền cần rút
     * @throws InsufficientBalanceException nếu số dư không đủ
     * @throws InvalidAmountException nếu amount <= 0
     */
    public abstract void withdraw(double amount)
            throws InsufficientBalanceException,
            InvalidAmountException;

    @Override
    public String toString() {
        return "Số tài khoản: " + accountNumber +
                ", Chủ tài khoản: " + holderName +
                ", Số dư: " + String.format("%.2f", balance);
    }
}