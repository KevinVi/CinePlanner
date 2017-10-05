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
public class JsonSearchResult {
    @JsonProperty("id")
    public int id;
    @JsonProperty("vote_count")
    public float vote_count;
    @JsonProperty("vote_average")
    public float vote_average;
    @JsonProperty("title")
    public String title;
    @JsonProperty("popularity")
    public float popularity;
    @JsonProperty("poster_path")
    public String poster_path;
    @JsonProperty("original_language")
    public String original_language;
    @JsonProperty("original_title")
    public String original_title;
    @JsonProperty("backdrop_path")
    public String backdrop_path;
    @JsonProperty("overview")
    public String overview;
    @JsonProperty("release_date")
    public String release_date;
    @JsonProperty("genre_ids")
    public List<JsonSearchGenre> genre_ids;
}
