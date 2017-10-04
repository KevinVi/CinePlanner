package com.example.cinemaplanner.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by Kevin on 30/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JsonGenderArray {
    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;
}
