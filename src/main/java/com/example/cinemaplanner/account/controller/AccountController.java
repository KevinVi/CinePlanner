package com.example.cinemaplanner.account.controller;

import com.example.cinemaplanner.account.authentication.AuthenticatedRequestBody;
import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.authentication.ConnectionRequestBody;
import com.example.cinemaplanner.account.authentication.Token;
import com.example.cinemaplanner.account.exceptions.AccountNotFoundException;
import com.example.cinemaplanner.account.exceptions.IncorrectCsvException;
import com.example.cinemaplanner.account.exceptions.MustBeAuthenticatedAsAdminException;
import com.example.cinemaplanner.account.exceptions.SamePasswordException;
import com.example.cinemaplanner.account.model.*;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.config.PasswordGenerator;
import com.example.cinemaplanner.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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
     * Get account information.
     *
     * @param token token user
     * @return Account Data
     */
    @RequestMapping(value = "/my-account", method = POST)
    public AccountData getMyAccount(@RequestBody Token token) {
        Account account = authenticationManager.getAccountFromToken(token.getToken());
        AccountPublic accountP = new AccountPublic(account);
        return new AccountData(accountP);
    }


    /**
     * Authenticate Account using login and password
     *
     * @param connectionRequestBody login and password
     * @return token
     */
    @RequestMapping(value = "/authenticate", method = POST)
    public Token authenticateAccount(@RequestBody ConnectionRequestBody connectionRequestBody) {
        return new Token(authenticationManager.getTokenByAuthentication(connectionRequestBody.getLogin(), connectionRequestBody.getPassword()));
    }


    /**
     * Change user password
     *
     * @param password new password and old password
     * @return token
     * @throws Exception issue
     */
    @RequestMapping(value = "/password", method = POST)
    public Token changePassword(@RequestBody AuthenticatedRequestBody<PasswordChange> password) throws Exception {
        authenticationManager.mustBeValidToken(password.getToken());
        accountService.validatePassword(password.getBody().getNewPassword());

        Account account = authenticationManager.getAccountFromToken(password.getToken());
        if (!accountService.isSamePassword(password.getBody().getOldPassword(), account.getPassword())) {
            throw new SamePasswordException();
        }
        account.setPassword(password.getBody().getNewPassword());
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
    public AccountPublic changeInfo(@RequestBody AuthenticatedRequestBody<UserInfo> info) {
        authenticationManager.mustBeValidToken(info.getToken());
        Account account = authenticationManager.getAccountFromToken(info.getToken());
        if (info.getBody().getFirstname() != null) {
            if (!info.getBody().getFirstname().isEmpty()) {
                if (!info.getBody().getFirstname().equals(account.getFirstName())) {
                    account.setFirstName(info.getBody().getFirstname());
                }
            }
        }
        if (info.getBody().getLastname() != null) {
            if (!info.getBody().getLastname().isEmpty()) {
                if (!info.getBody().getLastname().equals(account.getLastName())) {
                    account.setLastName(info.getBody().getLastname());
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
