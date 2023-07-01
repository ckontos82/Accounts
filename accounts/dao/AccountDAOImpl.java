package accounts.dao;

import accounts.model.Account;

import java.util.ArrayList;
import java.util.List;
public class AccountDAOImpl implements IAccountDAO {
    private static final List<Account> accounts = new ArrayList<>();

    @Override
    public Account insert(Account account) {
        accounts.add(account);
        return account;
    }

    @Override
    public Account update(Account account) {
        return accounts.set(accounts.indexOf(account), account);
    }

    @Override
    public Account get(Long id) {
        int pos = getIndexById(id);
        if (pos == -1) return null;
        return accounts.get(pos);
    }

    @Override
    public Account get(String iban) {
       int pos = getIndexByIban(iban);
       if (pos == -1) return null;
       return accounts.get(pos);
    }

    @Override
    public void delete(String iban) {
        accounts.removeIf(acc -> acc.getIban().equals(iban));
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public boolean ibanExists(String iban) {
        return getIndexByIban(iban) != -1;
    }

    @Override
    public boolean idExists(Long id) {
        return getIndexById(id) != -1;
    }

    @Override
    public boolean ssnExists(String ssn) {
        return getIndexBySsn(ssn) != -1;
    }

    /**
     * Returns the position in the ArrayList Datasource
     * of the Account having the input <code>iban</code>.
     *
     * @param iban
     *          the {@link Account#iban} to be searched.
     * @return
     *          the resulting position, or -1 if the iban
     *          is not found.
     */
    private int getIndexByIban(String iban) {
        int pos = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIban().equals(iban)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    /**
     * Returns the position in the ArrayList Datasource
     * of the Account having the input <code>iban</code>.
     *
     * @param id
     *          the {@link Account#id} to be searched.
     * @return
     *          the resulting position, or -1 if the id
     *          is not found.
     */
    private int getIndexById(Long id) {
        int pos = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(id)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    /**
     * Returns the position in the ArrayList Datasource
     * of the Account having the input <code>ssn</code>.
     *
     * @param ssn
     *          the {@link Account#ssn} to be searched.
     * @return
     *          the resulting position, or -1 if the id
     *          is not found.
     */
    private int getIndexBySsn(String ssn) {
        int pos = -1;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getHolder().getSsn().equals(ssn)) {
                pos = i;
                break;
            }
        }

        return pos;
    }
}
