package com.example.cinemaplanner.group.model;

import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 08/08/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreate {

    String name;
    List<String> users;
}
