package accounts.service.exceptions;

import accounts.model.Account;

public class IbanAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    public IbanAlreadyExistsException(Account account) {
        super("The account with IBAN " + account.getIban() + " already exists.");
    }

}
