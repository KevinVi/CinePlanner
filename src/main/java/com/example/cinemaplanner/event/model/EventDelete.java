package com.example.cinemaplanner.event.model;

import lombok.*;

/**
 * Created by Kevin on 27/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDelete {

    int idEvent;
    int idTeam;

}
