package accounts;

import accounts.dao.AccountDAOImpl;
import accounts.dto.AccountDTO;
import accounts.dto.UserDTO;
import accounts.model.Account;
import accounts.service.AccountServiceImpl;
import accounts.service.IAccountService;
import accounts.service.exceptions.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final IAccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        final int CREATE = 1;
        final int DEPOSIT = 2;
        final int WITHDRAW = 3;
        final int DELETE = 4;
        final int GET = 5;
        final int GETALL = 6;
        final int EXIT = 7;

        while (!exit) {
            System.out.println("1. Add Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Delete Account");
            System.out.println("5. Display Account Info");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case CREATE:
                        createAccount();
                        break;
                    case DEPOSIT:
                        deposit();
                        break;
                    case WITHDRAW:
                        withdraw();
                        break;
                    case DELETE:
                        deleteAccount();
                        break;
                    case GET:
                        getAccountInfo();
                        break;
                    case GETALL:
                        getAllAccountsInfo();
                        break;
                    case EXIT:
                        exit = true;
                        System.out.println("Goodbye!");
                        scanner.close();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            } catch (AccountNotFoundException | IbanAlreadyExistsException
                    | IdAlreadyExistsException | InsufficientAmountException
                    | InsufficientBalanceException | SsnNotValidException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void createAccount() throws IdAlreadyExistsException,
            IbanAlreadyExistsException {
        AccountDTO accountDTO = new AccountDTO();
        UserDTO userDTO = new UserDTO();

        System.out.println("Enter account details: ");
        System.out.println("Enter Account ID:");
        accountDTO.setAccountID(scanner.nextLong());
        scanner.nextLine();

        System.out.println("Enter IBAN:");
        accountDTO.setIban(scanner.nextLine());

        System.out.println("Enter Balance:");
        accountDTO.setBalance(scanner.nextDouble());
        scanner.nextLine();

        System.out.println("Enter User Details:");
        userDTO.setUserDetailsId(accountDTO.getAccountID());

        System.out.println("Enter First Name:");
        userDTO.setFirstname(scanner.nextLine());

        System.out.println("Enter Last Name:");
        userDTO.setLastname(scanner.nextLine());

        System.out.println("Enter SSN:");
        userDTO.setSsn(scanner.nextLine());

        accountDTO.setUserDTO(userDTO);

        try {
            accountService.insertAccount(accountDTO);
            System.out.println("Account successfully created.");
        } catch (IdAlreadyExistsException | IbanAlreadyExistsException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    private static void deposit() throws AccountNotFoundException,
            InsufficientAmountException, SsnNotValidException{
        System.out.println("Enter the amount to deposit:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        System.out.println("Enter the user's SSN:");
        String ssn = scanner.nextLine();

        System.out.println("Enter the user's IBAN:");
        String iban = scanner.nextLine();

        try {
            accountService.deposit(amount, iban, ssn);
        } catch (AccountNotFoundException | InsufficientAmountException | SsnNotValidException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static void withdraw() throws AccountNotFoundException,
            InsufficientAmountException, SsnNotValidException,
            InsufficientBalanceException {
        System.out.println("Enter the amount to withdraw:");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter the user's SSN:");
        String ssn = scanner.nextLine();

        System.out.println("Enter the user's IBAN:");
        String iban = scanner.nextLine();

        try {
            accountService.withdraw(amount, iban, ssn);
        } catch (AccountNotFoundException | InsufficientAmountException | SsnNotValidException
                 | InsufficientBalanceException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static void deleteAccount() {
        System.out.println("Enter the IBAN of the Account to be deleted:");
        String iban = scanner.nextLine();
        try {
            accountService.deleteAccount(iban);
            System.out.println("Account deleted successfully!");
        } catch (AccountNotFoundException e) {
            System.out.println("Failed to delete Account: " +e.getMessage());
        }
    }

    private static void getAccountInfo() throws AccountNotFoundException {
        System.out.println("Enter the IBAN of the account to be displayed:");
        String iban = scanner.nextLine();
        try {
            Account account = accountService.getAccount(iban);
            System.out.println("Account info: " + account);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static void getAllAccountsInfo() {
        List<Account> accounts = accountService.getAllAccounts();
        for (Account accountInfo : accounts) {
            System.out.println(accountInfo);
        }
    }
}
