package accounts.service.exceptions;

import accounts.model.Account;

public class IdAlreadyExistsException extends Exception {
    private final static long serialVersionUID = 1L;

    public IdAlreadyExistsException(Account account) {
        super("Account with id " + account.getId() + " already exists.");
    }
}
