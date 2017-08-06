package com.example.cinemaplanner.group.model;

import com.example.cinemaplanner.account.model.Account;
import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 06/08/2017 for ZKY.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupPublic {

    String name;
    List<Account> users;

    public GroupPublic(GroupModel groupModel) {
        this.name = groupModel.getName();
        //this.users = groupModel.getUsers();
    }
}
