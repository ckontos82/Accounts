package accounts.model;

public class Account extends AbstractEntity {
    private String iban;
    private User holder;
    private double balance;

    public Account() {}

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (Double.compare(account.getBalance(), getBalance()) != 0) return false;
        if (getIban() != null ? !getIban().equals(account.getIban()) : account.getIban() != null) return false;
        return getHolder() != null ? getHolder().equals(account.getHolder()) : account.getHolder() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getIban() != null ? getIban().hashCode() : 0;
        result = 31 * result + (getHolder() != null ? getHolder().hashCode() : 0);
        temp = Double.doubleToLongBits(getBalance());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", holder=" + holder +
                ", balance=" + balance +
                '}';
    }
}
