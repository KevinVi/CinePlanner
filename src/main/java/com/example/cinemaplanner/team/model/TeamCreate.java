package com.example.cinemaplanner.team.model;

import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 08/08/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamCreate {

    String name;
    List<String> users;
}
