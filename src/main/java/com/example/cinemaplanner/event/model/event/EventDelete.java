package com.example.cinemaplanner.event.model.event;

import lombok.*;

/**
 * Created by Kevin on 27/09/2017 for CinePlanner.
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
