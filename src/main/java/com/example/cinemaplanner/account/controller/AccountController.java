package com.example.cinemaplanner.account.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.authentication.Token;
import com.example.cinemaplanner.account.exceptions.AccountAlreadyExist;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.exceptions.SamePasswordException;
import com.example.cinemaplanner.account.model.*;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.config.PasswordGenerator;
import com.example.cinemaplanner.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Created by Andreï on 02/04/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final MailService emailService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AccountController(AccountService accountService, MailService emailService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Create new account.
     *
     * @param body info user
     * @return AccountPublic
     */
    @RequestMapping(value = "create", method = POST)
    public AccountPublic createAccount(@RequestBody BodyAccount body) throws Exception {
        Account accountExist = accountService.findByEmail(body.getLogin());
        if (accountExist == null) {
            if (body.getLogin() == null || body.getFirstName() == null || body.getLastName() == null || body.getPassword() == null) {
                throw new Exception("Wrong parameters");
            }
            Account account = Account.builder()
                    .login(body.getLogin())
                    .password(body.getPassword())
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .build();
            accountService.updateAccount(account);
            return new AccountPublic(account);
        } else {
            throw new AccountAlreadyExist();
        }

    }


    /**
     * Get account information.
     *
     * @param token token user
     * @return Account Data
     */
    @RequestMapping(value = "/my-account", method = POST)
    public AccountData getMyAccount(@RequestHeader(value = "token") String token) {
        Account account = authenticationManager.getAccountFromToken(token);
        AccountPublic accountP = new AccountPublic(account);
        return new AccountData(accountP);
    }


    /**
     * Authenticate Account using login and password.
     *
     * @param login    login
     * @param password password
     * @return token
     */
    @RequestMapping(value = "/authenticate", method = POST)
    public Token authenticateAccount(@RequestHeader(value = "login") String login, @RequestHeader(value = "password") String password) {
        return new Token(authenticationManager.getTokenByAuthentication(login, password));
    }


    /**
     * Change user password
     *
     * @param password new password and old password
     * @return token
     * @throws Exception issue
     */
    @RequestMapping(value = "/password", method = POST)
    public Token changePassword(@RequestHeader(value = "token") String token, @RequestBody PasswordChange password) throws Exception {
        authenticationManager.mustBeValidToken(token);
        accountService.validatePassword(password.getNewPassword());

        Account account = authenticationManager.getAccountFromToken(token);
        if (!accountService.isSamePassword(password.getOldPassword(), account.getPassword())) {
            throw new SamePasswordException();
        }
        account.setPassword(password.getNewPassword());
        accountService.updateAccount(account);

        return new Token(authenticationManager.getTokenByAccount(account));
    }


    /**
     * Change user info.
     *
     * @param info all needed info.
     * @return AccountPublic
     */
    @RequestMapping(value = "/update", method = POST)
    public AccountPublic changeInfo(@RequestHeader(value = "token") String token, @RequestBody UserInfo info) {
        authenticationManager.mustBeValidToken(token);
        Account account = authenticationManager.getAccountFromToken(token);
        if (info.getFirstname() != null) {
            if (!info.getFirstname().isEmpty()) {
                if (!info.getFirstname().equals(account.getFirstName())) {
                    account.setFirstName(info.getFirstname());
                }
            }
        }
        if (info.getLastname() != null) {
            if (!info.getLastname().isEmpty()) {
                if (!info.getLastname().equals(account.getLastName())) {
                    account.setLastName(info.getLastname());
                }
            }
        }

        accountService.saveAccount(account);
        return new AccountPublic(account);
    }


    /**
     * Send email checking if the person asked for new password
     *
     * @param email email user
     * @throws Exception Account not valid
     */
    @RequestMapping(value = "/restore", method = POST)
    public boolean askPassword(@RequestBody BodyEmail email) throws Exception {
        try {
            Account account = accountService.findByEmail(email.getEmail());
            if (account != null) {

                emailService.restorePasswordConfirmation(email.getEmail(), "localhost:8080/account/restore/" + email.getEmail() + "/" + account.getPassword());
                return true;
            } else {
                throw new AccountNotFoundException();
            }

        } catch (Exception e) {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Send new password using url formed by email and password hashed
     *
     * @param email email user
     * @param id    password user
     * @return boolean
     * @throws Exception Account not valid
     */
    @RequestMapping(value = "/restore/{email}/{id}", method = GET)
    public boolean restoreEmail(@PathVariable(value = "email") String email, @PathVariable(value = "id") String id) throws Exception {
        try {
            Account account = accountService.findByEmail(email);
            if (account != null) {
                if (account.getPassword().equals(id)) {
                    PasswordGenerator passwordGenerator = new PasswordGenerator();
                    String password = passwordGenerator.nextSessionId();
                    emailService.restorePasswordSend(email, password);
                    account.setPassword(password);
                    accountService.updateAccount(account);
                }
            } else {
                throw new AccountNotFoundException();
            }

        } catch (Exception e) {
            throw new AccountNotFoundException();
        }
        return true;
    }


}
