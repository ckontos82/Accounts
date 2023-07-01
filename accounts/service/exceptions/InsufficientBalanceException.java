package accounts.service.exceptions;

public class InsufficientBalanceException extends Exception {
    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException(double balance) {
        super("The balance " + balance + " is insufficient.");
    }
}
