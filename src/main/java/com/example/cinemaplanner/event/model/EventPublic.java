package com.example.cinemaplanner.event.model;

import com.example.cinemaplanner.group.model.GroupPublic;
import lombok.*;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Kevin on 13/08/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPublic {

    String name;
    String creator;
    String start;
    String end;

    public EventPublic(Event event) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.name = event.getName();
        this.creator = event.getCreator();
        this.start = dateFormat.format(event.getDtstart());
        this.end = dateFormat.format(event.getDtend());
    }
}
