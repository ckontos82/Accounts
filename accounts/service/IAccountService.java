package accounts.service;

import accounts.dto.AccountDTO;
import accounts.model.Account;
import accounts.model.User;
import accounts.service.exceptions.*;

import java.util.List;

public interface IAccountService {

    /**
     * Creates a {@link Account} based on the data carried by the {@link AccountDTO}.
     *
     * @param accountDTO
     *          the DTO that contains the account data.
     * @return  the resulting account
     * @throws IbanAlreadyExistsException
     *          if the {@link Account#iban} already exists in the datasource.
     * @throws IdAlreadyExistsException
     *          if the {@link Account#id} already exists.
     */
    Account insertAccount(AccountDTO accountDTO)
        throws IbanAlreadyExistsException, IdAlreadyExistsException;

    /**
     * Withdraws the specific amount from the {@link Account} based on the data
     * carried by the {@link AccountDTO}.
     *
     * @param amount
     *          the amount to be withdrawn.
     * @param iban
     *          the {@link Account#iban}.
     * @param ssn
     *          the {@link User#ssn}.
     * @return
     *          the resulting account.
     * @throws AccountNotFoundException
     *          if the {@link Account#iban} or the {@link Account#id} does not map
     *          to a {@link Account}.
     * @throws InsufficientAmountException
     *          if the amount is negative.
     * @throws InsufficientBalanceException
     *          if the amount supersedes the available balance.
     * @throws SsnNotValidException
     *          if the {@link Account#ssn} is not valid.
     */
    Account withdraw(double amount, String iban, String ssn)
            throws AccountNotFoundException, InsufficientAmountException,
            InsufficientBalanceException, SsnNotValidException;

    /**
     * Deposits the specific amount to the {@link Account} based on the data
     * carried by the {@link AccountDTO}.
     *
     * @param amount
     *          the amount to be deposited.
     * @param iban
     *          the {@link Account#iban}.
     * @param ssn
     *          the {@link User#ssn}.
     * @return
     *          the resulting account.
     * @throws AccountNotFoundException
     *          if the {@link Account#iban} or the {@link Account#id} does not mapt
     *          to a {@link Account}.
     * @throws InsufficientAmountException
     *          if the amount is negative.
     * @throws SsnNotValidException
     *          if the  {@link Account#ssn} is not valid.
     */
    Account deposit(double amount, String iban, String ssn)
            throws AccountNotFoundException, InsufficientAmountException, SsnNotValidException;

    /**
     * Removes a {@link Account} (i.e. the customer closes the account).
     *
     * @param iban
     *          the iban of the {@link Account} to be removed.
     * @throws AccountNotFoundException
     *          if the {@link Account#iban} does not lead to an
     *          existing {@link Account} in the datasource.
     */
    void deleteAccount(String iban) throws AccountNotFoundException;

    /**
     * Returns a {@link Account} based on the input iban.
     *
     * @param iban
     *          the iban of the {@link Account} to be returned.
     * @return
     *          the resulting {@link Account}.
     * @throws AccountNotFoundException
     *          if the {@link Account#iban} does not lead to an
     *          existing {@link Account} in the datasource.
     */
    Account getAccount(String iban) throws AccountNotFoundException;

    /**
     * Returns a {@link Account} based on the input id.
     *
     * @param id
     *          the iban of the {@link Account} to be returned.
     * @return
     *          the resulting {@link Account}.
     * @throws AccountNotFoundException
     *          if the {@link Account#id} does not lead to an
     *          existing {@link Account} in the datasource.
     */
    Account getAccount(Long id) throws AccountNotFoundException;

    /**
     * Returns all the {@link Account} instances of the datasource.
     *
     * @return
     *          the resulting {@link List<Account>}.
     */
    List<Account> getAllAccounts();
}
