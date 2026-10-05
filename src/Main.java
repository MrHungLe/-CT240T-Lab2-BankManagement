import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BankManager bankManager =
            new BankManager();

    public static void main(String[] args) {

        int choice;

        do {
            showMenu();

            try {
                choice = Integer.parseInt(
                        scanner.nextLine()
                );

                switch (choice) {

                    case 1:
                        createSavingAccount();
                        break;

                    case 2:
                        deposit();
                        break;

                    case 3:
                        withdraw();
                        break;

                    case 4:
                        transferMoney();
                        break;

                    case 5:
                        findAccount();
                        break;

                    case 6:
                        bankManager.displayAllAccounts();
                        break;

                    case 7:
                        showTotalBalance();
                        break;

                    case 0:
                        System.out.println(
                                "Đã thoát chương trình."
                        );
                        break;

                    default:
                        System.out.println(
                                "Lựa chọn không hợp lệ!"
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Lỗi: Vui lòng nhập số!"
                );

                choice = -1;

            } catch (InvalidAmountException e) {

                System.out.println(
                        "Lỗi số tiền: " + e.getMessage()
                );

                choice = -1;

            } catch (InsufficientBalanceException e) {

                System.out.println(
                        "Lỗi số dư: " + e.getMessage()
                );

                choice = -1;

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Lỗi: " + e.getMessage()
                );

                choice = -1;

            } catch (Exception e) {

                System.out.println(
                        "Lỗi không xác định: " +
                                e.getMessage()
                );

                choice = -1;
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void showMenu() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("     BANK ACCOUNT MANAGEMENT");
        System.out.println("=================================");
        System.out.println("1. Thêm tài khoản tiết kiệm");
        System.out.println("2. Nạp tiền");
        System.out.println("3. Rút tiền");
        System.out.println("4. Chuyển tiền");
        System.out.println("5. Tìm tài khoản");
        System.out.println("6. Hiển thị tất cả tài khoản");
        System.out.println("7. Tính tổng số dư");
        System.out.println("0. Thoát");
        System.out.println("=================================");
        System.out.print("Chọn chức năng: ");
    }

    private static void createSavingAccount() {

        System.out.print("Nhập số tài khoản: ");
        String accountNumber =
                scanner.nextLine();

        System.out.print("Nhập tên chủ tài khoản: ");
        String holderName =
                scanner.nextLine();

        System.out.print("Nhập số dư ban đầu: ");
        double balance =
                Double.parseDouble(scanner.nextLine());

        System.out.print("Nhập lãi suất (%): ");
        double interestRate =
                Double.parseDouble(scanner.nextLine());

        SavingAccount account =
                new SavingAccount(
                        accountNumber,
                        holderName,
                        balance,
                        interestRate
                );

        bankManager.addAccount(account);

        System.out.println(
                "Thêm tài khoản thành công!"
        );
    }

    private static void deposit()
            throws InvalidAmountException {

        System.out.print("Nhập số tài khoản: ");
        String accountNumber =
                scanner.nextLine();

        BankAccount account =
                bankManager.findAccount(accountNumber);

        if (account == null) {
            System.out.println(
                    "Không tìm thấy tài khoản!"
            );
            return;
        }

        System.out.print("Nhập số tiền nạp: ");
        double amount =
                Double.parseDouble(scanner.nextLine());

        account.deposit(amount);

        System.out.println(
                "Nạp tiền thành công!"
        );

        System.out.printf(
                "Số dư mới: %.2f%n",
                account.getBalance()
        );
    }

    private static void withdraw()
            throws InsufficientBalanceException,
            InvalidAmountException {

        System.out.print("Nhập số tài khoản: ");
        String accountNumber =
                scanner.nextLine();

        BankAccount account =
                bankManager.findAccount(accountNumber);

        if (account == null) {
            System.out.println(
                    "Không tìm thấy tài khoản!"
            );
            return;
        }

        System.out.print("Nhập số tiền rút: ");
        double amount =
                Double.parseDouble(scanner.nextLine());

        account.withdraw(amount);

        System.out.println(
                "Rút tiền thành công!"
        );

        System.out.printf(
                "Số dư mới: %.2f%n",
                account.getBalance()
        );
    }

    private static void transferMoney()
            throws InsufficientBalanceException,
            InvalidAmountException {

        System.out.print(
                "Nhập tài khoản nguồn: "
        );

        String fromAcc =
                scanner.nextLine();

        System.out.print(
                "Nhập tài khoản đích: "
        );

        String toAcc =
                scanner.nextLine();

        System.out.print(
                "Nhập số tiền chuyển: "
        );

        double amount =
                Double.parseDouble(scanner.nextLine());

        bankManager.transferMoney(
                fromAcc,
                toAcc,
                amount
        );

        System.out.println(
                "Chuyển tiền thành công!"
        );
    }

    private static void findAccount() {

        System.out.print(
                "Nhập số tài khoản cần tìm: "
        );

        String accountNumber =
                scanner.nextLine();

        BankAccount account =
                bankManager.findAccount(
                        accountNumber
                );

        if (account == null) {
            System.out.println(
                    "Không tìm thấy tài khoản!"
            );
        } else {
            System.out.println(
                    "Thông tin tài khoản:"
            );
            System.out.println(account);
        }
    }

    private static void showTotalBalance() {

        double total =
                bankManager.calculateTotalBalance(
                        bankManager.getAllAccounts()
                );

        System.out.printf(
                "Tổng số dư toàn hệ thống: %.2f VNĐ%n",
                total
        );
    }
}