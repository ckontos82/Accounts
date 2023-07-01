package accounts.service.exceptions;

import accounts.model.Account;

public class AccountNotFoundException extends Exception {
    private final static long serialVersionUID = 1;

    public AccountNotFoundException(String iban) {
        super("The account with iban " + iban +" cannot be found.");
    }

    public AccountNotFoundException(Long id) {
        super("The account with id " + id + " cannot be found.");
    }

    public AccountNotFoundException(Account account) {
        super("The account with iban " + account.getIban() + " cannot be found.");
    }
}
