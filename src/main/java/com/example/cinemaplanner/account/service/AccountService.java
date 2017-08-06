package com.example.cinemaplanner.account.service;

import com.example.cinemaplanner.account.exceptions.AccountFieldNotValidException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andreï on 02/04/2017 for ZKY.
 * Service to prepare some action of repository requests
 */

//TODO secret key
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private ShaPasswordEncoder encoder;
    private static final int ShaStrength = 256;
    private static final String secretKey = "SpringSecretKeyESGI";

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        encoder = new ShaPasswordEncoder(ShaStrength);
    }

    /**
     * Get All accounts.
     *
     * @return List of accounts
     */
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Update Account password by encoding it.
     *
     * @param account account user.
     */
    public void updateAccount(Account account) {
        account.setPassword(encoder.encodePassword(account.getPassword(), secretKey));
        accountRepository.save(account);
    }

    /**
     * Save account in db.
     *
     * @param account account.
     */
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    /**
     * GEt account by Id
     *
     * @param id id account
     * @return Account
     */
    public Account getAccountById(int id) {
        Account account;
        try {
            account = accountRepository.findById(id).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return account;
    }

    /**
     * Get account using login.
     *
     * @param login login user
     * @return account
     */
    public Account getAccountByLogin(String login) {
        Account account;
        try {
            account = accountRepository.findByLogin(login);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return account;
    }

    /**
     * get Account using login adn password
     *
     * @param login    login user
     * @param password password user
     * @return account
     */
    public Account getAccountByLoginAndPassword(String login, String password) {
        Account account;
        String hashedPassword;

        account = getAccountByLogin(login);
        if (account == null) {
            return null;
        }

        hashedPassword = encoder.encodePassword(password, secretKey);
        if (!hashedPassword.equals(account.getPassword())) {
            return null;
        }

        return account;
    }

    /**
     * Check if the password is the same as the one in db.
     *
     * @param password     new password user
     * @param realPassword old password user
     * @return boolean
     */
    public boolean isSamePassword(String password, String realPassword) {
        String hashedPassword = encoder.encodePassword(password, secretKey);
        return hashedPassword.equals(realPassword);
    }


    /**
     * Condition for valid login
     *
     * @param login login
     */
    public void validateLogin(String login) {
        if (login == null)
            throw new AccountFieldNotValidException("Account's login has to be set");
        if (login.length() < 3)
            throw new AccountFieldNotValidException("Account's login is too short");

    }

    /**
     * Condition for valid password
     *
     * @param password login
     */
    public void validatePassword(String password) {
        if (password == null)
            throw new AccountFieldNotValidException("Account's password has to be set");
        if (password.length() < 3)
            throw new AccountFieldNotValidException("Account's password is too short");
        if (password.length() > 25)
            throw new AccountFieldNotValidException("Account's password is too long");
    }

    /**
     * save account using list of accounts and set up passowrd
     *
     * @param accounts account list
     */
    public void saveAccounts(List<Account> accounts) {
        for (Account account :
                accounts) {
            updateAccount(account);
        }
    }
    /**
     * Find Account using email.
     *
     * @param email email
     * @return account
     */
    public Account findByEmail(String email) {
        return accountRepository.findByLogin(email);
    }
}
