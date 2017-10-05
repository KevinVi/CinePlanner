package com.example.cinemaplanner.event.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 24/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JsonSearchPage {
    @JsonProperty("results")
    public List<JsonSearchResult> results;

}
