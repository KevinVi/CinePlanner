package com.example.cinemaplanner.group.controller;

import com.example.cinemaplanner.account.authentication.AuthenticationManager;
import com.example.cinemaplanner.account.service.AccountService;
import com.example.cinemaplanner.group.model.GroupPublic;
import com.example.cinemaplanner.group.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@RestController
@CrossOrigin
@RequestMapping("/group")
public class GroupController {
    private final GroupRepository groupRepository;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public GroupController(GroupRepository groupRepository, AccountService accountService, AuthenticationManager authenticationManager) {
        this.groupRepository = groupRepository;
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "create", method = POST)
    public List<GroupPublic> getGroups() {
        return null;
    }
}
