import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankManager {

    /**
     * Map quản lý tài khoản theo số tài khoản.
     *
     * Key   : accountNumber
     * Value : BankAccount
     */
    private final Map<String, BankAccount> accounts;

    public BankManager() {
        accounts = new HashMap<>();
    }

    /**
     * Thêm tài khoản.
     *
     * @param account tài khoản cần thêm
     */
    public void addAccount(BankAccount account) {

        if (account == null) {
            throw new IllegalArgumentException(
                    "Tài khoản không được null!"
            );
        }

        if (accounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException(
                    "Số tài khoản đã tồn tại!"
            );
        }

        accounts.put(
                account.getAccountNumber(),
                account
        );
    }

    /**
     * Tìm tài khoản theo số tài khoản.
     *
     * @param accountNumber số tài khoản
     * @return tài khoản tương ứng hoặc null
     */
    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Chuyển tiền giữa hai tài khoản.
     *
     * Pre-condition:
     * - Tài khoản nguồn tồn tại.
     * - Tài khoản đích tồn tại.
     * - amount > 0.
     * - Tài khoản nguồn đủ tiền.
     *
     * Post-condition:
     * - Tài khoản nguồn giảm amount.
     * - Tài khoản đích tăng amount.
     *
     * @param fromAcc tài khoản nguồn
     * @param toAcc tài khoản đích
     * @param amount số tiền chuyển
     */
    public void transferMoney(
            String fromAcc,
            String toAcc,
            double amount)
            throws InsufficientBalanceException,
            InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Số tiền chuyển phải lớn hơn 0!"
            );
        }

        BankAccount source = findAccount(fromAcc);
        BankAccount destination = findAccount(toAcc);

        if (source == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy tài khoản nguồn!"
            );
        }

        if (destination == null) {
            throw new IllegalArgumentException(
                    "Không tìm thấy tài khoản đích!"
            );
        }

        if (fromAcc.equals(toAcc)) {
            throw new IllegalArgumentException(
                    "Tài khoản nguồn và tài khoản đích không được giống nhau!"
            );
        }

        /*
         * Rút tiền trước.
         * Nếu tài khoản nguồn không đủ tiền,
         * exception được ném ra và tài khoản đích
         * chưa được cộng tiền.
         */
        source.withdraw(amount);

        try {
            destination.deposit(amount);
        } catch (InvalidAmountException e) {

            /*
             * Rollback nếu thao tác nạp tiền thất bại.
             */
            try {
                source.deposit(amount);
            } catch (InvalidAmountException rollbackException) {
                throw new IllegalStateException(
                        "Không thể rollback giao dịch!",
                        rollbackException
                );
            }

            throw e;
        }
    }

    /**
     * Tính tổng số dư của danh sách tài khoản.
     *
     * Producer Extends:
     * List<? extends BankAccount>
     *
     * @param accounts danh sách tài khoản
     * @return tổng số dư
     */
    public double calculateTotalBalance(
            List<? extends BankAccount> accounts) {

        double total = 0.0;

        for (BankAccount acc : accounts) {
            if (acc != null) {
                total += acc.getBalance();
            }
        }

        return total;
    }

    /**
     * Lấy toàn bộ tài khoản.
     *
     * @return danh sách tài khoản
     */
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * Hiển thị toàn bộ tài khoản.
     */
    public void displayAllAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("Chưa có tài khoản nào.");
            return;
        }

        for (BankAccount account : accounts.values()) {
            System.out.println(account);
        }
    }
}