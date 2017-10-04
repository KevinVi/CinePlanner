package com.example.cinemaplanner.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by Kevin on 24/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JsonUrlImg {
    @JsonProperty("secure_base_url")
    public String url;
}
