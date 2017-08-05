package com.example.cinemaplanner.config;

import com.example.cinemaplanner.account.model.Account;
import com.example.cinemaplanner.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Kevin on 09/07/2017 for ZKY for ZKY.
 * Create at least one account when the server start
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final AccountService accountService;


    @Autowired
    public DataLoader(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * insert admin account if not existed
     * @param args args
     */
    public void run(ApplicationArguments args) {
        if (accountService.getAccounts().isEmpty()) {
            Account account = Account.builder().id(0).login("kevin.vivor@gmail.com").password("cXNH44GxgG").firstName("kevin").lastName("vivor").build();
            accountService.updateAccount(account);
        }
    }
}