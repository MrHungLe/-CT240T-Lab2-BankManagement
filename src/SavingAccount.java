/**
 * Tài khoản tiết kiệm.
 *
 * Invariant mở rộng:
 * balance >= 50,000 VNĐ
 */
public class SavingAccount extends BankAccount {

    private double interestRate;

    private static final double MIN_BALANCE = 50000;

    public SavingAccount(
            String accountNumber,
            String holderName,
            double balance,
            double interestRate) {

        super(accountNumber, holderName, balance);

        if (balance < MIN_BALANCE) {
            throw new IllegalArgumentException(
                    "Tài khoản tiết kiệm phải có số dư tối thiểu 50.000 VNĐ!"
            );
        }

        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Rút tiền từ tài khoản tiết kiệm.
     *
     * Pre-condition:
     * amount > 0
     * balance - amount >= 50,000
     *
     * Post-condition:
     * balance_new = balance_old - amount
     *
     * Invariant:
     * balance_new >= 50,000
     */
    @Override
    public void withdraw(double amount)
            throws InsufficientBalanceException,
            InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Số tiền rút phải lớn hơn 0!"
            );
        }

        double oldBalance = getBalance();
        double newBalance = oldBalance - amount;

        if (newBalance < MIN_BALANCE) {
            throw new InsufficientBalanceException(
                    "Không thể rút tiền! " +
                            "Số dư sau khi rút phải tối thiểu 50.000 VNĐ."
            );
        }

        setBalance(newBalance);

        // Post-condition
        if (getBalance() != oldBalance - amount) {
            throw new IllegalStateException(
                    "Vi phạm hậu điều kiện của withdraw!"
            );
        }

        // Invariant
        if (getBalance() < MIN_BALANCE) {
            throw new IllegalStateException(
                    "Vi phạm invariant của SavingAccount!"
            );
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Lãi suất: " +
                String.format("%.2f%%", interestRate);
    }
}