package accounts.dao;

import accounts.model.Account;

import java.util.List;

public interface IAccountDAO {

    /**
     * Inserts a new {@link Account} in the datasource.
     *
     * @param account
     *          the model object that contains the account.
     * @return
     *          the resulting {@link Account}.
     */
    Account insert(Account account);

    /**
     * Updates an {@link Account}.
     *
     * @param account
     *          the object that contains the account data.
     * @return
     *          the resulting {@link Account}.
     */
    Account update(Account account);

    /**
     * Returns an {@link Account} instance from the datasource
     * based on the input id.
     *
     * @param id
     *          the {@link Account#id} to be returned.
     * @return
     *          the resulting {@link Account}.
     */
    Account get(Long id);

    /**
     * Returns an {@link Account} instance from the datasource
     * based on the input iban.
     *
     * @param id
     *          the {@link Account#iban} to be returned.
     * @return
     *          the resulting {@link Account}.
     */
    Account get(String iban);

    /**
     * Removes an {@link Account} from the datasources (if the account is closed).
     *
     * @param iban
     *          the IBAN of the account to be removed.
     */
    void delete(String iban);

    /**
     * Returns all the {@link Account} from the datasource
     *
     * @return
     *          the resulting {@link List<Account>}
     */
    List<Account> getAll();
    
    /**
     * Checks if a IBAN already exists in the datasource as part of a {@link Account}.
     *
     * @param iban
     *          the <code>iban</code> to be searched
     * @return
     *          true if <code>iban</code> exists in a {@link Account}
     */
    boolean ibanExists(String iban);

    /**
     * Checks if an id already exists in the datasource as part of a {@link Account}.
     *
     * @param id
     *          the <code>id</code> to be searched
     * @return
     *          true if <code>id</code> exists in a {@link Account}
     */
    boolean idExists(Long id);

    /**
     * Checks if an SSN already exists in the datasource as part of a {@link Account}.
     *
     * @param ssn
     *          the <code>ssn</code> to be searched
     * @return
     *          true if <code>ssn</code> exists in a {@link Account}
     */
    boolean ssnExists(String ssn);
}
