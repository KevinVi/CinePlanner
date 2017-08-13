package com.example.cinemaplanner.group.model;

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
    String creator;
    List<String> pendingUsers;

    public GroupPublic(Group group) {
        this.name = group.getName();
        this.creator = group.getCreator();
        this.pendingUsers = group.getPendingUsers();
    }
}
