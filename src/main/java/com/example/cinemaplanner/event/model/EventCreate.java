package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.group.model.GroupPublic;
import lombok.*;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCreate {

    String name;
    String start;
    String end;
    int idGroup;
}
