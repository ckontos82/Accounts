package accounts.service;

import accounts.dao.IAccountDAO;
import accounts.dto.AccountDTO;
import accounts.dto.UserDTO;
import accounts.model.Account;
import accounts.model.User;
import accounts.service.exceptions.*;

import java.util.List;

public class AccountServiceImpl implements IAccountService {
    private final IAccountDAO dao;

    public AccountServiceImpl(IAccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Account insertAccount(AccountDTO accountDTO) throws IbanAlreadyExistsException,
            IdAlreadyExistsException {
        Account account;
        try {
            account = new Account();
            mapAccount(account, accountDTO);

            if (dao.ibanExists(accountDTO.getIban())) {
                throw new IbanAlreadyExistsException(account);
            }
            if (dao.idExists(accountDTO.getAccountID())) {
                throw new IdAlreadyExistsException(account);
            }

            return dao.insert(account);
        } catch (IbanAlreadyExistsException | IdAlreadyExistsException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account withdraw(AccountDTO accountDTO, double amount, String ssn)
            throws AccountNotFoundException, InsufficientAmountException, InsufficientBalanceException,
            SsnNotValidException {
        Account account;
        try {
            account = new Account();
            mapAccount(account, accountDTO);

            if (!dao.idExists(accountDTO.getAccountID())) {
                throw new AccountNotFoundException(account);
            }

            if (!(dao.get(accountDTO.getAccountID()).getHolder().getSsn().equals(ssn))) {
                throw new SsnNotValidException(ssn);
            }

            if (amount <= 0) {
                throw new InsufficientAmountException(amount);
            }

            if (accountDTO.getBalance() - amount < 0) {
                throw new InsufficientBalanceException(accountDTO.getBalance());
            }

            accountDTO.setBalance(accountDTO.getBalance() - amount);
            account.setBalance(accountDTO.getBalance());

            return dao.update(account);

        } catch (AccountNotFoundException | InsufficientBalanceException | InsufficientAmountException
                 | SsnNotValidException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account deposit(AccountDTO accountDTO, double amount, String ssn)
            throws AccountNotFoundException, InsufficientAmountException, SsnNotValidException {
        Account account;
        try {
            account = new Account();
            mapAccount(account, accountDTO);

            if (!dao.idExists(accountDTO.getAccountID())) {
                throw new AccountNotFoundException(account);
            }

            if (!dao.get(accountDTO.getAccountID()).getHolder().getSsn().equals(ssn)) {
                throw new SsnNotValidException(ssn);
            }

            if (amount <= 0) {
                throw new InsufficientAmountException(amount);
            }

            accountDTO.setBalance(accountDTO.getBalance() + amount);
            account.setBalance(accountDTO.getBalance());

            return dao.update(account);

        } catch (AccountNotFoundException | InsufficientAmountException | SsnNotValidException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteAccount(String iban) throws AccountNotFoundException {
        Account account;
        try {
            account = new Account();

            if(!dao.ibanExists(iban)) {
                throw new AccountNotFoundException(iban);
            }

            dao.delete(iban);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account getAccount(String iban) throws AccountNotFoundException {
        Account account;
        try{
            account = dao.get(iban);
            if (account == null) {
                throw new AccountNotFoundException(iban);
            }
            return dao.get(iban);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account getAccount(Long id) throws AccountNotFoundException {
        Account account;
        try{
            account = dao.get(id);
            if (account == null) {
                throw new AccountNotFoundException(id);
            }
            return dao.get(id);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return dao.getAll();
    }

    /**
     * Maps {@link AccountDTO} to {@link Account}.
     * @param account
     *          the {@link Account} under creation.
     * @param accountDTO
     *          the Account Data Transfer Object.
     */
    private void mapAccount(Account account, AccountDTO accountDTO) {
        account.setId(accountDTO.getAccountID());
        account.setIban(accountDTO.getIban());
        account.setBalance(accountDTO.getBalance());
        User user = new User();
        mapUser(user, accountDTO.getUserDTO());
        account.setHolder(user);
    }

    /**
     * Maps {@link UserDTO} to {@link User}.
     * @param user
     *          the {@link User} under creation.
     * @param userDTO
     *          the User Data Transfer Object.
     */
    private void mapUser(User user, UserDTO userDTO) {
        user.setId(userDTO.getUserDetailsId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setSsn(userDTO.getSsn());
    }
}
