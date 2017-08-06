package com.example.cinemaplanner.account.repository;
import com.example.cinemaplanner.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Andreï on 02/04/2017 for ZKY.
 * Repository for Account
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{

    List<Account> findAll();
    Account findByLogin( String login );
    List<Account> findById( int id );
    Account findAccountByPassword(String password);
}
