package com.example.cinemaplanner.account.authentication;
import com.example.cinemaplanner.account.exceptions.AuthenticationNotValidException;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedAsAdminException;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedException;
import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Andreï on 10/04/2017 for ZKY.
 */
@Component
public class AuthenticationManager {

    private final AccountService accountService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthenticationManager(AccountService accountService, TokenProvider tokenProvider) {
        this.accountService = accountService;
        this.tokenProvider = tokenProvider;
    }


    /**
     * Get Token by authentication using login and password.
     *
     * @param login    login user
     * @param password password user
     * @return token
     */
    public String getTokenByAuthentication(String login, String password) {
        Account account = getAccountByAuthentication(login, password);
        if (account == null) {
            throw new AuthenticationNotValidException();
        }

        return getTokenByAccount(account);
    }

    /**
     * Get Token by account
     *
     * @param account account user
     * @return token
     */
    public String getTokenByAccount(Account account) {
        return tokenProvider.getToken(account);
    }

    /**
     * Get Account by login and password.
     *
     * @param login    login user
     * @param password password user
     * @return account
     */
    private Account getAccountByAuthentication(String login, String password) {
        return accountService.getAccountByLoginAndPassword(login, password);
    }

    /**
     * Get Account using token.
     *
     * @param token current token user
     * @return account
     */
    public Account getAccountFromToken(String token) {
        return tokenProvider.getAccountFromToken(token);
    }

    /**
     * Check if the token is valid.
     *
     * @param token token
     */
    public void mustBeValidToken(String token) {
        Account account = getAccountFromToken(token);
        if (account == null) {
            throw new MustBeAuthenticatedException();
        }
    }

}
